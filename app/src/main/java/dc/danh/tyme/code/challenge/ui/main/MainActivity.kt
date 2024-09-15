package dc.danh.tyme.code.challenge.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dc.danh.tyme.code.challenge.databinding.ActivityMainBinding
import dc.danh.tyme.code.challenge.ui.detail.DetailActivity
import dc.danh.tyme.code.challenge.utils.addOnScrollListener

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { login ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("login", login)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = null
        }
        binding.recyclerView.addOnScrollListener {
            // End of the list reached, fetch more users
            viewModel.fetchMoreUsers()
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { users ->
            userAdapter.submitList(users)
        }
    }
}
