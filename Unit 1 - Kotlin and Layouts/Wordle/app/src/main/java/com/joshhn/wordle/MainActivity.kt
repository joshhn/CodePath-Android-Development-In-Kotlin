package com.joshhn.wordle


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.color
import com.github.jinatonic.confetti.CommonConfetti
import com.joshhn.wordle.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val getWord = FourLetterWordList()
    private var wordToGuess = ""
    private var counter = 0
    private var streak = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnStart?.setOnClickListener {
            //Generate keyword
            //wordToGuess = getWord.getRandomFourLetterWord()
            wordToGuess = "HOME"
            binding?.tvKeyword?.text = wordToGuess
            counter = 0

            binding?.btnStart?.visibility = View.INVISIBLE
            binding?.etInput?.visibility = View.VISIBLE
            binding?.btnSubmit?.visibility = View.VISIBLE
            binding?.tvLabel1?.visibility = View.VISIBLE
            binding?.btnReset?.visibility = View.VISIBLE
            binding?.tvStreak?.visibility = View.VISIBLE
        }

        binding?.btnSubmit?.setOnClickListener {
            //Take input from user
            val editText = binding?.etInput
            val inputValue = editText?.text.toString().uppercase()

            if(inputValue.length > 3 && isValidInput(inputValue)){
                counter++

                hideKeyboard()
                editText?.setText("")

                if(checkWin(inputValue)){
                    streak++
                    binding?.tvStreak?.text = "Streak x$streak"
                }

                val answer = checkGuess(inputValue)
                val modifiedAnswer = customCheckedGuess(answer, inputValue)

                if(counter == 1){
                    binding?.tvGuess1?.text = inputValue
                    binding?.tvCheck1?.text = modifiedAnswer
                    binding?.tvGuess1?.visibility = View.VISIBLE
                    binding?.tvCheckLabel1?.visibility = View.VISIBLE
                    binding?.tvCheck1?.visibility = View.VISIBLE
                }else if(counter == 2){
                    binding?.tvGuess2?.text = inputValue
                    binding?.tvCheck2?.text = modifiedAnswer
                    binding?.tvLabel2?.visibility = View.VISIBLE
                    binding?.tvGuess2?.visibility = View.VISIBLE
                    binding?.tvCheckLabel2?.visibility = View.VISIBLE
                    binding?.tvCheck2?.visibility = View.VISIBLE
                }else if(counter == 3) {
                    if(!checkWin(inputValue)){
                        Toast.makeText(this, "You lose! Let's do it again!", Toast.LENGTH_SHORT).show()
                        streak = 0
                        binding?.tvStreak?.text = "Streak x$streak"
                    }
                    binding?.tvGuess3?.text = inputValue
                    binding?.tvCheck3?.text = modifiedAnswer
                    binding?.tvLabel3?.visibility = View.VISIBLE
                    binding?.tvGuess3?.visibility = View.VISIBLE
                    binding?.tvCheckLabel3?.visibility = View.VISIBLE
                    binding?.tvCheck3?.visibility = View.VISIBLE
                    binding?.tvKeyword?.visibility = View.VISIBLE
                    binding?.btnSubmit?.isClickable = false
                    binding?.btnRestart?.visibility = View.VISIBLE
                }
            }else {
                Toast.makeText(this, "Keyword must contain 4 letters and be from A to Z only", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.btnRestart?.setOnClickListener {
            setupRestart()
        }

        binding?.btnReset?.setOnClickListener {
            setupRestart()
            streak = 0
            binding?.tvStreak?.text = "Streak x$streak"
        }
    }

    private fun isValidInput(inputValue: String): Boolean {
        for(i in 0..3){
            if(inputValue[i] < 'A' || inputValue[i] > 'Z' ){
                return false
            }
        }
        return true
    }

    private fun checkWin(inputValue: String): Boolean {
        if(counter < 4 && inputValue == wordToGuess){
            CommonConfetti.rainingConfetti(binding?.root, intArrayOf(Color.GREEN,Color.BLACK, Color.YELLOW, Color.RED, Color.GRAY, Color.WHITE, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.LTGRAY, Color.MAGENTA))
                .stream(5000)
            binding?.btnSubmit?.isClickable = false
            binding?.btnRestart?.visibility = View.VISIBLE
            hideKeyboard()
            Toast.makeText(this, "Congratulations! You win!", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun setupRestart() {
        binding?.btnStart?.visibility = View.VISIBLE
        binding?.etInput?.visibility = View.INVISIBLE
        binding?.btnSubmit?.visibility = View.INVISIBLE
        binding?.tvLabel1?.visibility = View.INVISIBLE
        binding?.tvLabel2?.visibility = View.INVISIBLE
        binding?.tvLabel3?.visibility = View.INVISIBLE
        binding?.tvCheckLabel1?.visibility = View.INVISIBLE
        binding?.tvCheckLabel2?.visibility = View.INVISIBLE
        binding?.tvCheckLabel3?.visibility = View.INVISIBLE
        binding?.tvCheck1?.visibility = View.INVISIBLE
        binding?.tvCheck2?.visibility = View.INVISIBLE
        binding?.tvCheck3?.visibility = View.INVISIBLE
        binding?.tvGuess1?.visibility = View.INVISIBLE
        binding?.tvGuess2?.visibility = View.INVISIBLE
        binding?.tvGuess3?.visibility = View.INVISIBLE
        binding?.tvKeyword?.visibility = View.INVISIBLE
        binding?.btnRestart?.visibility = View.INVISIBLE
        binding?.btnReset?.visibility = View.INVISIBLE
        binding?.tvStreak?.visibility = View.INVISIBLE
        binding?.btnSubmit?.isClickable = true
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

    private fun customCheckedGuess(checkedGuess: String, guess: String) : SpannableStringBuilder? {

        //Method 1 (not work right)
//        val spannableString = SpannableString(guess)
//        val correctFColor = ForegroundColorSpan(ContextCompat.getColor(this@MainActivity, R.color.correctColor))
//        val wrongFColor = ForegroundColorSpan(ContextCompat.getColor(this@MainActivity, R.color.wrongColor))
//        val existFColor = ForegroundColorSpan(ContextCompat.getColor(this@MainActivity, R.color.existColor))
//
//        for (i in 0..3) {
//            if (checkedGuess[i] == 'O') {
//                spannableString.setSpan(correctFColor,i,i+1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//            else if (checkedGuess[i] == '+') {
//                spannableString.setSpan(existFColor,i,i+1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//            else{
//                spannableString.setSpan(wrongFColor,i,i+1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
//            }
//        }
//        return spannableString


        //Method 2 (https://stackoverflow.com/questions/10828182/spannablestringbuilder-to-create-string-with-multiple-fonts-text-sizes-etc-examp)
        val correctColor = ContextCompat.getColor(this@MainActivity, R.color.correctColor)
        val wrongColor = ContextCompat.getColor(this@MainActivity, R.color.wrongColor)
        val existColor = ContextCompat.getColor(this@MainActivity, R.color.existColor)

        var modifiedAnswer = SpannableStringBuilder()
            .append("")

        for (i in 0..3) {
            if (checkedGuess[i] == 'O') {
                modifiedAnswer = modifiedAnswer
                    .color(correctColor) { append(guess[i]) }
            }
            else if (checkedGuess[i] == '+') {
                modifiedAnswer = modifiedAnswer
                    .color(existColor) { append(guess[i]) }
            }
            else{
                modifiedAnswer = modifiedAnswer
                    .color(wrongColor) { append(guess[i]) }
            }
        }

        return modifiedAnswer
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}