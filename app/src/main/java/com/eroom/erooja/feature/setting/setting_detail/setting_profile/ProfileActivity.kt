package com.eroom.erooja.feature.setting.setting_detail.setting_profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityChangeProfileSettingBinding
import org.koin.android.ext.android.get

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private lateinit var binding: ActivityChangeProfileSettingBinding
    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()

    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_profile_setting)
        binding.profileSetting = this@ProfileActivity
    }

    private fun initView(){
        presenter = ProfilePresenter(this, get())
        presenter.getProfileImage()
    }

    override fun setProfileImage(imagePath: String?) {

       imagePath?.let{ binding.circleImageView.loadUrlCenterCrop(imagePath)
           this.toastShort(imagePath)}
           ?:run{ binding.circleImageView.loadDrawable(resources.getDrawable(R.drawable.account, null)) } //dummy
    }

    fun pickFromImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Consts.PICK_FROM_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    fun back(v: View){
        finish()
    }
}