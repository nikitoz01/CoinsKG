package kg.mobile.coins.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.mobile.coins.R


class AboutAppFragment : Fragment(R.layout.fragment_about_app) {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_about_app, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.show()
        super.onDestroyView()
    }
}