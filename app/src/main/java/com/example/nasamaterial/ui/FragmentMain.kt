package com.example.nasamaterial.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nasamaterial.Constants
import com.example.nasamaterial.viewModel.FragmentMainViewModel
import com.example.nasamaterial.PictureLoadState
import com.example.nasamaterial.R
import com.example.nasamaterial.databinding.FragmentMainBinding
import com.example.nasamaterial.dto.NasaApodDTO
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso

class FragmentMain : Fragment() {

    private val viewModel: FragmentMainViewModel by lazy {
        ViewModelProvider(this).get(FragmentMainViewModel::class.java)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var isExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        initialiseFAB()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initialiseFAB() {
        startState()
        binding.btnStop.setOnClickListener {
            if (isExpanded) {
                binding.imgBtnStop.setImageResource(R.drawable.ic_baseline_back_hand_24)
                collapseFAB()
            } else {
                expandFAB()
                binding.imgBtnStop.setImageResource(R.drawable.ic_baseline_close_24)
            }
        }
    }

    private fun startState() {
        binding.btnMountains.apply {
            alpha = 0f
            isClickable = false
        }
        binding.btnFloatingChips.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() {
        isExpanded = true
        ObjectAnimator.ofFloat(binding.imgBtnStop, "rotationY", 0f, 360f).start()
        ObjectAnimator.ofFloat(binding.btnFloatingChips, "translationX", -250f).start()
        ObjectAnimator.ofFloat(binding.btnMountains, "translationY", -250f).start()

        binding.btnFloatingChips.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.btnFloatingChips.isClickable = true
                    binding.btnFloatingChips.setOnClickListener {
                        requireActivity()
                            .supportFragmentManager.beginTransaction()
                            .add(R.id.container, FragmentChips())
                            .addToBackStack("")
                            .commit()
                    }
                }
            })
        binding.btnMountains.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.btnMountains.isClickable = true
                    binding.btnMountains.setOnClickListener {
                        val intent = Intent(requireContext(), CollapsingToolbarActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
    }

    private fun collapseFAB() {
        isExpanded = false
        ObjectAnimator.ofFloat(binding.imgBtnStop, "rotationY", 360f, 0f).start()
        ObjectAnimator.ofFloat(binding.btnFloatingChips, "translationX", 0f).start()
        ObjectAnimator.ofFloat(binding.btnMountains, "translationY", 0f).start()

        binding.btnFloatingChips.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.btnFloatingChips.isClickable = false
                    binding.btnMountains.setOnClickListener(null)
                }
            })
        binding.btnMountains.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.btnMountains.isClickable = false
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheetBehavior(binding.bottomSheet.bottomSheetContainer)

        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getRemoteSource(Constants.API_KEY)

        binding.btnTab.setOnClickListener {
            val intent = Intent(requireContext(), PagerActivity::class.java)
            startActivity(intent)
        }

        binding.iconRecyclerview.setOnClickListener {
            val intent = Intent(requireContext(), RecyclerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(state: PictureLoadState) {
        when (state) {
            is PictureLoadState.Success<*> -> {
                val serverResponseData = state.nasaData
                if (serverResponseData is NasaApodDTO) {
                    val url = serverResponseData.url
                    if (url.isNullOrEmpty()) {
                        //showError("Сообщение, что ссылка пустая")
                        Toast.makeText(requireContext(), "Link is empty", Toast.LENGTH_LONG).show()
                    } else {
                        //showSuccess()
                        binding.bottomSheet.bottomSheetDescriptionHeader.text =
                            serverResponseData.title

                        binding.bottomSheet.bottomSheetDescription.text =
                            serverResponseData.explanation

                        if (serverResponseData.media_type.equals("video")) {
                            binding.img.setImageResource(R.drawable.ic_camera_recorder_no_video)
                        } else {
                            Picasso
                                .get()
                                .load(url)
                                .into(binding.img)
                        }
                    }
                }
            }
            is PictureLoadState.Loading -> {
            }
            is PictureLoadState.Error -> {
                Toast.makeText(requireContext(), state.error.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}