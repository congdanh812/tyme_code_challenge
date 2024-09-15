package dc.danh.tyme.code.challenge.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dc.danh.tyme.code.challenge.R
import dc.danh.tyme.code.challenge.databinding.ActivityDetailBinding

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        initView()
        initListener()
        observeViewModel()
    }

    private fun initView() {
        val login = intent.getStringExtra("login") ?: return
        viewModel.getUserDetail(login)
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun observeViewModel() {
        viewModel.userDetail.observe(this) { userDetail ->
            userDetail?.let { user ->
                binding.tvLoginName.text = user.login
                user.location?.let {
                    binding.tvLocation.text = it
                    binding.ivLocation.visibility = if (it.isNotEmpty()) View.VISIBLE else View.INVISIBLE
                }
                binding.tvUserStats.text = String.format(getString(R.string.user_stats), user.following, user.followers)
                binding.tvBlogContent.text = user.htmlUrl
                Glide.with(this).load(userDetail.avatarUrl).circleCrop().into(binding.ivAvatar)
            }
        }
    }
}
