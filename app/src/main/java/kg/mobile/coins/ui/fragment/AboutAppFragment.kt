package kg.mobile.coins.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.mobile.coins.R


class AboutAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_about_app, container, false)
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.show()
        super.onDestroyView()
    }
}