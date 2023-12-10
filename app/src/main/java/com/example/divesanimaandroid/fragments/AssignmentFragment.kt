package com.example.divesanimaandroid.fragments

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.adapter.TodoAdapter
import com.example.divesanimaandroid.utils.bitmap.BitmapConverter
import com.example.divesanimaandroid.utils.http.DivesAnimaClient
import com.example.divesanimaandroid.utils.log.Logger
import com.example.divesanimaandroid.utils.toast.ToastUtil
import kotlinx.android.synthetic.main.fragment_assignment.imageButtonAddTodo
import kotlinx.android.synthetic.main.fragment_assignment.imageViewDailyImage
import kotlinx.android.synthetic.main.fragment_assignment.todoRecycler
import kotlinx.coroutines.launch

class AssignmentFragment : Fragment() {

    private val log = Logger.getLogger(javaClass)

    private lateinit var divesAnimaClient: DivesAnimaClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        divesAnimaClient = DivesAnimaClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.getPreferences(MODE_PRIVATE)?.getString(getString(R.string.sp_jwt), "")
            ?.let { token ->
                lifecycleScope.launch {
                    divesAnimaClient.getDailyImage(token)?.let { dailyImage ->
                        log.info("image id: ${dailyImage.id}")

                        BitmapConverter.bitmapFromStringByteArray(dailyImage.image)?.let { bmp ->
                            imageViewDailyImage.setImageBitmap(
                                Bitmap.createBitmap(bmp)
                            )
                        }
                    }

                    refreshTodos(token)

                    imageButtonAddTodo.setOnClickListener {
                        val editText = EditText(requireContext())
                        val dialog = AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.add_record))
                            .setView(editText)
                            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                                lifecycleScope.launch {
                                    divesAnimaClient.addTodo(token, editText.text.toString())
                                    ToastUtil.show(
                                        requireContext(),
                                        getString(R.string.todo_created)
                                    )

                                    refreshTodos(token)
                                }
                            }
                            .setNegativeButton(R.string.back, null)
                            .create()
                        dialog.show()
                    }
                }
            }
    }

    private suspend fun refreshTodos(token: String) {
        divesAnimaClient.getTodos(token)?.let { todos ->
            log.info(todos.toString())

            todoRecycler.adapter =
                TodoAdapter(requireContext(), divesAnimaClient, token, todos.toMutableList())
            todoRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}