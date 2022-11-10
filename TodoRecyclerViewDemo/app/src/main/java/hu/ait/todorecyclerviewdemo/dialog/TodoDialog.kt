package hu.ait.todorecyclerviewdemo.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import hu.ait.todorecyclerviewdemo.ScrollingActivity
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.databinding.TodoDialogBinding
import java.util.*

class TodoDialog : DialogFragment() {

    interface TodoDialogHandler {
        public fun todoCreated(todo: Todo)

        public fun todoUpdated(todo: Todo)
    }

    lateinit var todoDialogHandler: TodoDialogHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TodoDialogHandler) {
            todoDialogHandler = context
        } else {
            throw java.lang.RuntimeException(
                "The activity does not implement the TodoHandler interface"
            )
        }
    }


    private var isEditMode = false

    private lateinit var dialogViewBinding: TodoDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        // Are we in edit mode? - Have we received a Todo object to edit?
        if (arguments != null && requireArguments().containsKey(
                ScrollingActivity.KEY_TODO_EDIT
            )
        ) {
            isEditMode = true
            dialogBuilder.setTitle("Edit Todo")
        } else {
            isEditMode = false
            dialogBuilder.setTitle("New Todo")
        }

        dialogViewBinding = TodoDialogBinding.inflate(
            requireActivity().layoutInflater
        )
        dialogBuilder.setView(dialogViewBinding.root)

        // pre-fill the dialog if we are in edit mode
        if (isEditMode) {
            val todoToEdit =
                requireArguments().getSerializable(
                    ScrollingActivity.KEY_TODO_EDIT
                ) as Todo

            dialogViewBinding.etTodoText.setText(todoToEdit.todoTitle)
            dialogViewBinding.cbTodoDone.isChecked = todoToEdit.isDone
        }


        dialogBuilder.setPositiveButton("Ok") { dialog, which ->
            //
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->
        }


        return dialogBuilder.create()
    }


    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            if (dialogViewBinding.etTodoText.text.isNotEmpty()) {
                if (isEditMode) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }

                dialog.dismiss()
            } else {
                dialogViewBinding.etTodoText.error = "This field can not be empty"
            }
        }
    }


    fun handleItemCreate() {
        todoDialogHandler.todoCreated(
            Todo(
                null,
                dialogViewBinding.etTodoText.text.toString(),
                Date(System.currentTimeMillis()).toString(),
                dialogViewBinding.cbTodoDone.isChecked
            )
        )
    }

    fun handleItemEdit() {
        val todoToEdit =
            (requireArguments().getSerializable(
                ScrollingActivity.KEY_TODO_EDIT
            ) as Todo).copy(
                todoTitle = dialogViewBinding.etTodoText.text.toString(),
                isDone = dialogViewBinding.cbTodoDone.isChecked
            )
        todoDialogHandler.todoUpdated(todoToEdit)
    }

}