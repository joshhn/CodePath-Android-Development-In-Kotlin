package com.joshhn.wordle


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joshhn.wordle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val getWord = FourLetterWordList()
    private var wordToGuess = ""
    private var counter = 0
    private var answer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Generate keyword
        wordToGuess = getWord.getRandomFourLetterWord()
        binding?.tvKeyword?.text = wordToGuess

        binding?.btnSubmit?.setOnClickListener {
            //Take input from user
            val editText = binding?.etInput
            val inputValue = editText?.text.toString().uppercase()

            if(inputValue.length > 3){
                //TODO: Implement checking function
                counter++

                hideKeyboard()
                editText?.setText("")

                answer = checkGuess(inputValue)
                if(counter == 1){
                    binding?.tvGuess1?.text = inputValue
                    binding?.tvCheck1?.text = answer
                    binding?.tvGuess1?.visibility = View.VISIBLE
                    binding?.tvCheckLabel1?.visibility = View.VISIBLE
                    binding?.tvCheck1?.visibility = View.VISIBLE
                }else if(counter == 2){
                    binding?.tvGuess2?.text = inputValue
                    binding?.tvCheck2?.text = answer
                    binding?.tvLabel2?.visibility = View.VISIBLE
                    binding?.tvGuess2?.visibility = View.VISIBLE
                    binding?.tvCheckLabel2?.visibility = View.VISIBLE
                    binding?.tvCheck2?.visibility = View.VISIBLE
                }else if(counter == 3) {
                    binding?.tvGuess3?.text = inputValue
                    binding?.tvCheck3?.text = answer
                    binding?.tvLabel3?.visibility = View.VISIBLE
                    binding?.tvGuess3?.visibility = View.VISIBLE
                    binding?.tvCheckLabel3?.visibility = View.VISIBLE
                    binding?.tvCheck3?.visibility = View.VISIBLE
                    binding?.tvKeyword?.visibility = View.VISIBLE
                    binding?.btnSubmit?.isClickable = false
                }
            }else {
                Toast.makeText(this, "Keyword must contain 4 letters", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */

    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}