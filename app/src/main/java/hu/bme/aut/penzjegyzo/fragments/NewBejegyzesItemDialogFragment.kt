package hu.bme.aut.penzjegyzo.fragments


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.penzjegyzo.R
import hu.bme.aut.penzjegyzo.data.Bejegyzes
import hu.bme.aut.penzjegyzo.databinding.DialogNewBejegyzesItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NewBejegyzesItemDialogFragment : DialogFragment() {

    interface NewBejegyzesItemDialogListener {
        fun onBejegyzesItemCreated(newItem: Bejegyzes)
    }

    private lateinit var listener: NewBejegyzesItemDialogListener

    private lateinit var binding: DialogNewBejegyzesItemBinding

    private var sdf = SimpleDateFormat("dd-MM-yyyy",Locale.getDefault())


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? NewBejegyzesItemDialogListener
            ?: throw RuntimeException("Fragment must implement the NewBejegyzesItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogNewBejegyzesItemBinding.inflate(LayoutInflater.from(context))
        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )
        binding.spMethod.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.method_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_bejegyzes_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onBejegyzesItemCreated(getBejegyzesItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }
    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getBejegyzesItem() = Bejegyzes(
        name = binding.etName.text.toString(),
        description = binding.etDescription.text.toString(),
        value = binding.etValue.text.toString().toIntOrNull() ?: 0,
        category = Bejegyzes.Category.getByOrdinal(binding.spCategory.selectedItemPosition)
            ?: Bejegyzes.Category.BEVÉTEL,
        pay_method = Bejegyzes.Method.getByOrdinal(binding.spMethod.selectedItemPosition)
            ?: Bejegyzes.Method.KÁRTYA,
        date = sdf.format(Date())
    )

    companion object {
        const val TAG = "NewBejegyzesItemDialogFragment"
    }
}