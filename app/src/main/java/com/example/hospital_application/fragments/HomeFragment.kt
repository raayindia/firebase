package com.example.hospital_application.fragments

import MyModel
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital_application.Responses.AddstafResponse
import com.example.hospital_application.Responses.ProductModel
import com.example.hospital_application.adapters.ItemAdapter
import com.example.tablayout.UpdateDelAdd.ProductListAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Stack
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.hospital_application.ApiClient
import com.example.hospital_application.Api_Interface
import com.example.hospital_application.R
import com.example.hospital_application.Responses.DetailXX
import com.example.hospital_application.Responses.ViewStaffResponse
import com.example.tablayout.UpdateDelAdd.OnStaffClick


class HomeFragment : Fragment(), ItemAdapter.ItemClickListener, OnStaffClick {

    private var bottomSheetStateListener: BottomSheetStateListener? = null
    private lateinit var addstafresponse: AddstafResponse
    private lateinit var recyclerView: RecyclerView

    //    private lateinit var adapter: ItemAdapter
    private lateinit var deleteIcon: ImageView
    lateinit var floatingBtn: FloatingActionButton
    private lateinit var addProduct: Button
    private lateinit var addtitle: TextView
    private lateinit var nameEditText: EditText
    private lateinit var designationEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var clear: ImageView

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val selectedItems = mutableListOf<MyModel>()
    private lateinit var mProductListAdapter: ProductListAdapter
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var selectedStaffHashmap: HashMap<String, String>
    var selectedArrayList = ArrayList<String>()
    private var modelToBeUpdated: Stack<ProductModel> = Stack()
    private var isEditClick: Boolean = false
    private lateinit var staffIdFromModel: String

    interface BottomSheetStateListener {
        fun onBottomSheetStateChanged(isVisible: Boolean)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recy)
        floatingBtn = view.findViewById(R.id.floatingbtn)
        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheet))
        clear = view.findViewById(R.id.clear)
        mFragmentManager =
            parentFragmentManager // Initialize mFragmentManager using parentFragmentManager
        nameEditText = view.findViewById<EditText>(R.id.btmsheetNameET)
        designationEditText = view.findViewById<EditText>(R.id.btmsheetDesignationET)
        phoneNumberEditText = view.findViewById<EditText>(R.id.btmsheetContactNbrET)
        emailEditText = view.findViewById<EditText>(R.id.btmsheetEmailET)
        addProduct = view.findViewById(R.id.btmSheetAddBtn)
        addtitle = view.findViewById<TextView>(R.id.tvTitleAddStaff)
        addProduct.setText(R.string.add)
        addtitle.setText(R.string.addTitle)
        selectedStaffHashmap = HashMap()

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        BottomSheetBehavior.STATE_COLLAPSED
                        floatingBtn.visibility = View.VISIBLE
                        bottomSheetStateListener?.onBottomSheetStateChanged(false)
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        floatingBtn.visibility = View.GONE
                        bottomSheetBehavior.peekHeight = 0 // Fully expanded, no peeking
                        bottomSheetStateListener?.onBottomSheetStateChanged(true)
                    }
                }
            }
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
        floatingBtn.setOnClickListener {

            val state = if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                BottomSheetBehavior.STATE_COLLAPSED
            } else {
                BottomSheetBehavior.STATE_EXPANDED
            }
            bottomSheetBehavior.state = state
        }
        staffIdFromModel="0"
        addProduct.setOnClickListener {
            addAndUpdateStaffDetails(isEditClick, staffIdFromModel)
        }

        clear.setOnClickListener {
            val state = if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                BottomSheetBehavior.STATE_COLLAPSED
            } else {
                BottomSheetBehavior.STATE_EXPANDED
            }

            bottomSheetBehavior.state = state

            nameEditText.setText("")
            designationEditText.setText("")
            phoneNumberEditText.setText("")
            emailEditText.setText("")
            addProduct.setText(R.string.add)
            addtitle.setText(R.string.addTitle)
        }

        deleteIcon = view.findViewById(R.id.delete)
        // Set an initial color for the delete icon (e.g., grey)
        deleteIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.disabled))

        // Set an initial color for the delete icon (e.g., grey)
        deleteIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.disabled))
        deleteIcon.setOnClickListener {
            deleteStaffCards(selectedArrayList)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        viewStaffCards()
    }

    private fun addAndUpdateStaffDetails(update: Boolean, staffId: String) {
        val name = nameEditText.text.toString()
        val phone = phoneNumberEditText.text.toString()
        val designation = designationEditText.text.toString()
        val email = emailEditText.text.toString()
        if (update) {
            updateStaff(name, designation, staffId, phone)
            nameEditText.setText("")
            designationEditText.setText("")
            phoneNumberEditText.setText("")
            emailEditText.setText("")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            floatingBtn.visibility = View.VISIBLE
        } else {
            if (!name.isBlank() && !phone.isBlank() && !designation.isBlank() && !email.isBlank()) {
                addStaff(name, designation, phone, email)
            }
            nameEditText.setText("")
            designationEditText.setText("")
            phoneNumberEditText.setText("")
            emailEditText.setText("")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            floatingBtn.visibility = View.VISIBLE
        }
    }

    private fun updateStaff(name: String, designation: String, staffId: String, phone: String) {
        val hospitalid = "45"
        val updateStaff = ApiClient.buildService(Api_Interface::class.java)
        val requestCall =
            updateStaff.updatesafedetailsfromhome(staffId, hospitalid, name, designation, phone)
        requestCall.enqueue(object : Callback<AddstafResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<AddstafResponse>,
                response: Response<AddstafResponse>
            ) {
                if (response.isSuccessful) {
                    val viewStaffResponse: AddstafResponse? = response.body()
                    if (viewStaffResponse != null) {
                        viewStaffCards()
                        Toast.makeText(
                            requireContext(),
                            viewStaffResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<AddstafResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    private fun addStaff(name: String, designation: String, phone: String, email: String) {
        val hospitalid = "45"
        val hospitalname = "adsfadfa"
        val addStaff = ApiClient.buildService(Api_Interface::class.java)
        val requestCall =
            addStaff.addstafdetails(hospitalid, hospitalname, name, designation, phone, email)
        requestCall.enqueue(object : Callback<AddstafResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<AddstafResponse>,
                response: Response<AddstafResponse>
            ) {
                if (response.isSuccessful) {
                    val viewStaffResponse: AddstafResponse? = response.body()
                    if (viewStaffResponse != null) {
                        viewStaffCards()
                        Toast.makeText(
                            requireContext(),
                            viewStaffResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<AddstafResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    private fun viewStaffCards() {

        val hospitalid = "45"
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.viewStaffDetails(hospitalid)
        requestCall.enqueue(object : Callback<ViewStaffResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<ViewStaffResponse>,
                response: Response<ViewStaffResponse>
            ) {
                if (response.isSuccessful) {
                    val viewStaffResponse: ViewStaffResponse? = response.body()
                    viewStaffResponse?.let { staffResponse ->
                        val staffList: List<DetailXX> = staffResponse.Details
                        val adapter =
                            context?.let {
                                ProductListAdapter(
                                    it,
                                    mFragmentManager,
                                    this@HomeFragment,
                                    staffList
                                )
                            }
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<ViewStaffResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    private fun deleteStaffCards(current: ArrayList<String>) {
        for (i in 0 until current.size) {
            selectedStaffHashmap.put("sid[" + i + "]", current.get(i).toString())
        }
        val hospitalid = "45"
        val deleteService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = deleteService.deletestaff(hospitalid, selectedStaffHashmap)
        requestCall.enqueue(object : Callback<AddstafResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<AddstafResponse>,
                response: Response<AddstafResponse>
            ) {
                if (response.isSuccessful) {
                    val addstafResponse: AddstafResponse? = response.body()
                    Toast.makeText(
                        requireContext(),
                        addstafResponse?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewStaffCards()
                }
            }

            override fun onFailure(call: Call<AddstafResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showaddstafcard(
        hospitalid: String = "45"
    ) {

        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.viewadedstafdetails(hospitalid)
        requestCall.enqueue(object : Callback<AddstafResponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<AddstafResponse>,
                response: Response<AddstafResponse>
            ) {
                if (response.isSuccessful) { // Status code between 200 to 299
                    addstafresponse = response.body()!!


                } else if (response.code() == 401) { // Unauthorized

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.session_exp),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                } else { // Application-level failure

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<AddstafResponse>, t: Throwable) {

                Toast.makeText(
                    requireContext(),
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }


    @SuppressLint("MissingInflatedId")
    private fun showServerErrorDialog() {
        val dialogView = layoutInflater.inflate(R.layout.error_dialog, null)
        val errorTextView = dialogView.findViewById<TextView>(R.id.errorMessage)
        val dismissButton = dialogView.findViewById<Button>(R.id.dismissButton)

        errorTextView.text = addstafresponse.message

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()

        dismissButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onItemClick(item: MyModel) {
        updateSelectedItems(item)
    }

    // Update the selected items list when a checkbox is clicked
    fun updateSelectedItems(item: MyModel) {
        if (item.isChecked) {
            selectedItems.add(item)
        } else {
            selectedItems.remove(item)
        }

        // Check if there are selected items and update the delete icon color accordingly
        if (selectedItems.isNotEmpty()) {
            deleteIcon.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorTab1Selected
                )
            )
        } else {
            deleteIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.disabled))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomSheetStateListener) {
            bottomSheetStateListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        bottomSheetStateListener = null
    }


    override fun onUpdate(model: DetailXX, isUpdate: Boolean) {
        Toast.makeText(requireContext(), "" + model.staffname, Toast.LENGTH_SHORT).show()
        val state = if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.STATE_COLLAPSED
        } else {
            BottomSheetBehavior.STATE_EXPANDED
        }
        bottomSheetBehavior.state = state

        nameEditText.setText(model.staffname)
        designationEditText.setText(model.staffdesignation.toString())
        phoneNumberEditText.setText(model.staffphone.toString())
        emailEditText.setText(model.staffemail)
        if (isUpdate) {
            addProduct.setText(R.string.update)
        }
        isEditClick = isUpdate
        staffIdFromModel = model.staffid.toString()
        addtitle.setText(R.string.updateTitle)
        // addAndUpdateStaffDetails(isUpdate,model.staffid.toString())
    }


    override fun onDelete(model: DetailXX, current: ArrayList<String>) {
        selectedArrayList = current
        Toast.makeText(requireContext(), "" + selectedArrayList, Toast.LENGTH_SHORT).show()
    }

    override fun onStaffSelected(model: DetailXX, staffId: Int) {

    }


}

