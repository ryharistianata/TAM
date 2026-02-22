package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tam.ui.theme.TAMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TAMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Ruddy Haristianata",
                        npm = "2457051011",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    npm: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "\n \n \t \tHello $name!\n \t \tNPM: $npm" +
                "\n\n" +
                "\t \tSIAP BELAJAR COMPOSE" +
                "\n\n \t \tGerak Alam \n \n" +
                "GerakAlam merupakan aplikasi yang membantu masyarakat menemukan berbagai kegiatan sosial seperti volunteer, kegiatan komunitas, aksi lingkungan, serta informasi pendakian dan gunung. Aplikasi ini bertujuan memudahkan pengguna untuk berpartisipasi dalam kegiatan sosial sekaligus meningkatkan kepedulian terhadap alam dan lingkungan.",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TAMTheme {
        Greeting(
            name = "Ruddy Haristianata",
            npm = "2457051011"
        )
    }
}
