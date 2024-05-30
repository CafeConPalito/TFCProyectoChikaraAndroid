package com.cafeconpalito.chikara.ui.myChicks


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cafeconpalito.chikara.databinding.FragmentMyChicksBinding
import com.cafeconpalito.chikara.ui.findChicks.FindChicksAdapter
import com.cafeconpalito.chikara.ui.findChicks.FindChicksViewModel
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.utils.isKeyboardVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [MyChicksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MyChicksFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentMyChicksBinding? = null
    private val binding get() = _binding!!

    private val mcViewModel : MyChicksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyChicksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initRecyclerView()
    }

    private fun initListeners() {

        //EDIT TEXT AL DARLE BUSCAR
        binding.etFindChick.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    //TODO AL DARLE BUSCAR LLAMAR A LA API
                    true
                }
                else -> false
            }
        }
    }

    private fun initRecyclerView() {

        Log.d("FindChicks", " UserChickFragment -> initRecyclerView()")

        CoroutineScope(Dispatchers.IO).launch {

            mcViewModel.getUserChicks()

            withContext(Dispatchers.Main) {
                mcViewModel.userChicksLiveData.observe(viewLifecycleOwner, { ListTopChicks ->
                    with(binding.rvFindChick) {
                        //Selecciono el tipo de Layout para el RV
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        //Paso la nueva lista de datos!
                        adapter = MyChicksAdapter(ListTopChicks) {
                            /*
                        //CREO QUE ESTO NO ES NECESARIO
                        val intentDetail = Intent(context, DetailActivity::class.java)
                        intentDetail.putExtra(EXTRA, it)
                        startActivity(intentDetail)
                        */
                        }

                    }
                })

                //Mientras no carge estara sin ser visible...
                mcViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
                    Log.d("UserChicks", " UserChickFragment -> isLoagind!")
                    //TODO POR SI QUEREMOS AÑADIR UN PROGRESS BAR
                })
            }
        }
    }

}