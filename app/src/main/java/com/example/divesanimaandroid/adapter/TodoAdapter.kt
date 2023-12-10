package com.example.divesanimaandroid.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.models.dto.response.TodoItemResponse
import com.example.divesanimaandroid.utils.http.DivesAnimaClient
import com.example.divesanimaandroid.utils.toast.ToastUtil
import kotlinx.android.synthetic.main.todo_recycler_item.view.checkBoxTodo
import kotlinx.android.synthetic.main.todo_recycler_item.view.imageButtonDelete
import kotlinx.coroutines.runBlocking

class TodoAdapter(
    private val context: Context,
    private val divesAnimaClient: DivesAnimaClient,
    private val token: String,
    private val todos: MutableList<TodoItemResponse>
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBoxTodo: CheckBox
        val imageButtonDelete: ImageButton

        init {
            checkBoxTodo = view.checkBoxTodo
            imageButtonDelete = view.imageButtonDelete
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_recycler_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkBoxTodo.text = todos[position].record
        holder.checkBoxTodo.isChecked = todos[position].completed

        if (todos[position].completed)
            holder.checkBoxTodo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        else holder.checkBoxTodo.paintFlags = Paint.LINEAR_TEXT_FLAG

        holder.checkBoxTodo.setOnCheckedChangeListener { _, _ ->
            runBlocking {
                val response = divesAnimaClient.changeTodoCompleted(token, todos[position].id)

                if(response?.completed!!)
                    holder.checkBoxTodo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                else holder.checkBoxTodo.paintFlags = Paint.LINEAR_TEXT_FLAG
            }
        }

        holder.imageButtonDelete.setOnClickListener {
            runBlocking {
                divesAnimaClient.deleteTodo(token, todos[position].id)
                divesAnimaClient.getTodos(token)?.let { todosResponse ->
                    todos.clear()
                    todos.addAll(todosResponse)
                    notifyDataSetChanged()
                    ToastUtil.show(context, context.getString(R.string.todo_deleted))
                }
            }
        }
    }

    override fun getItemCount(): Int = todos.size

}