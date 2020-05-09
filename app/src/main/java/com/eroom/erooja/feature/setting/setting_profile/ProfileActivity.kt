package com.eroom.erooja.feature.setting.setting_profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.*
import com.eroom.erooja.databinding.ActivityChangeProfileSettingBinding
import org.koin.android.ext.android.get
import timber.log.Timber
import android.content.Context


class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private lateinit var binding: ActivityChangeProfileSettingBinding
    private lateinit var presenter: ProfilePresenter
    var uploadCheck = ObservableField(false)
    private lateinit var imagePath : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, com.eroom.erooja.R.layout.activity_change_profile_setting)
        binding.profileSetting = this@ProfileActivity
    }

    private fun initView(){
        presenter = ProfilePresenter(this, get(), get())
        presenter.getProfileImage()
    }

    override fun setProfileImage(imagePath: String?) {

       imagePath?.let{ binding.circleImageView.loadUrlCenterCrop(imagePath)
           this.toastShort(imagePath)}
           ?:run{ binding.circleImageView.loadDrawable(resources.getDrawable(com.eroom.erooja.R.drawable.ic_icon_users_blank, null)) }
    }

    fun pickFromImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Consts.PICK_FROM_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Consts.PICK_FROM_IMAGE && resultCode == RESULT_OK) {
            try {
                imagePath = data?.data!!
                binding.circleImageView.loadUri(imagePath)
                uploadCheck.set(true)
                this.toastLong(getFileNameByUri(this,imagePath))


            } catch (e: Exception) {
               Timber.i("PROFILE ACTIVITY IMAGE LOAD ERROR")
            }
        } else if (resultCode == RESULT_CANCELED) {
            Timber.i("사진 선택 취소")
        }
    }

    fun saveProfile(v: View){
        presenter.updateProfileImage(getFileNameByUri(this,imagePath))
        finish()
    }


    fun back(v: View){
        finish()
    }

    fun getFileNameByUri(context: Context, uri: Uri): String {
        var fileName = "unknown"//default fileName
        var filePathUri = uri
        if (uri.scheme!!.toString().compareTo("content") == 0) {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            if (cursor!!.moveToFirst()) {
                val column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index))
                fileName = filePathUri.lastPathSegment!!.toString()
            }
        } else if (uri.scheme!!.compareTo("file") == 0) {
            fileName = filePathUri.lastPathSegment!!.toString()
        } else {
            fileName = fileName + "_" + filePathUri.lastPathSegment
        }
        return fileName
    }


}