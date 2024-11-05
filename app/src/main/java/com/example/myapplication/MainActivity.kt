package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.Photo
import com.example.myapplication.model.Todo
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.AppViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel= viewModel<AppViewModel>()
                val apiResult by viewModel.photos.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize().padding(start = 4.dp, end = 4.dp),
                        contentAlignment = Alignment.Center) {
                        when(apiResult){
                            is ApiResult.Loading ->{
                                CircularProgressIndicator(modifier = Modifier.size(70.dp), color = Color.Blue)
                            }
                            is ApiResult.Error ->{
                                Toast.makeText(this@MainActivity,apiResult.error , Toast.LENGTH_SHORT).show()
                            }
                            is ApiResult.Success ->{
                                val list = apiResult.data ?: emptyList()
                                LazyColumn(modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)){
                                    items(items = list){ ph ->
                                        PhotoItem(photo = ph)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun TodoItem(todo: Todo) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement =Arrangement.spacedBy(4.dp) ) {
        Text(text = todo.id.toString())
        Text(text = todo.title ?: "title", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = todo.completed.toString(), fontSize = 18.sp)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}
@Composable
fun PhotoItem(photo: Photo) {
    val matrix = ColorMatrix()
    matrix.setToSaturation(0F)
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement =Arrangement.spacedBy(4.dp) ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.url)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            colorFilter = ColorFilter.colorMatrix(matrix)
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoItem(Todo(1,1,"test",true))
}