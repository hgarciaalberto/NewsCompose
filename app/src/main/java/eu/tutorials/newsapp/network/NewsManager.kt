package eu.tutorials.newsapp.network

import androidx.compose.runtime.*
import eu.tutorials.newsapp.model.ArticleCategory
import eu.tutorials.newsapp.model.getArticleCategory
import eu.tutorials.newsapp.network.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse =
        mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    private val _getArticleByCategory =
        mutableStateOf(TopNewsResponse())
    val getArticleByCategory:MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    //Todo 5: remove the api key
    private fun getArticles(){
        val service = Api.retrofitService.getTopArticles("us")
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _newsResponse.value = response.body()!!
                }else{

                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

            }

        })
    }

    fun getArticlesByCategory(category: String){
        val client = Api.retrofitService.getArticlesByCategories(category)
        client.enqueue(object :Callback<TopNewsResponse>{
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if (response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                }else{

                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {

            }

        })
    }
    fun onSelectedCategoryChanged(category:String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}