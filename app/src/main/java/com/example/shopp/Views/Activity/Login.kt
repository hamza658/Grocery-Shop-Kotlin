package com.example.shopp.Views.Activity
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.shopp.R
import com.example.shopp.Utils.*
import com.example.shopp.ViewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    private lateinit var txtForgetPassword: TextView
    private lateinit var btnGoToSignUp: TextView
    //
    private lateinit var txtEmailLayout: TextInputLayout
    private lateinit var txtPasswordLayout: TextInputLayout
    private lateinit var txtEmail: TextInputEditText
    private lateinit var txtPwd: TextInputEditText
    //
    private lateinit var cbRememberMe: CheckBox
    private lateinit var ProgressbarLogin: ProgressBar
    private lateinit var btnLogin: Button
    private lateinit var AllOfPageLogin: RelativeLayout
    //
    val ReadyFunction = ReadyFunction()
    val Validator = Validator()
    //
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var MySharedPref: SharedPreferences
    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        //
        MySharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //
        initView();
        SetLastEmail()
        DoActionLogin()
        CLickToHidKeyBoard()
        GoToSignUp()
        GoToForget()
        //
        SetAnimation()
    }

    fun SetAnimation()
    {
        //Animation
        ReadyFunction.animationFadeInOut(txtEmailLayout,800)
        ReadyFunction.animationFadeInOut(txtPasswordLayout,1500)
        ReadyFunction.animationFadeInOut(cbRememberMe,1500)

        ReadyFunction.animationFadeIn(btnLogin,1600)
        ReadyFunction.animationFadeIn(txtForgetPassword,500)
        ReadyFunction.animationFadeIn(btnGoToSignUp,600)
    }

    fun SetLastEmail()
    {
        val LastEmail = MySharedPref.getString(EMAILUSER, null)
        txtEmail.setText(LastEmail)
    }

    @SuppressLint("WrongViewCast", "UseSwitchCompatOrMaterialCode")
    fun initView() {
        btnLogin = findViewById(R.id.btnLogin)
        btnGoToSignUp = findViewById(R.id.btnGoToSignUp)
        txtForgetPassword = findViewById(R.id.txtForgetPassword)
        //
        txtEmailLayout = findViewById(R.id.txtEmailLayout)
        txtPasswordLayout = findViewById(R.id.txtPasswordLayout)
        txtEmail = findViewById(R.id.txtEmail)
        txtPwd = findViewById(R.id.txtPwd)
        //
        cbRememberMe = findViewById(R.id.comboRememberMe)
        ProgressbarLogin = findViewById(R.id.progressbar_login)
        AllOfPageLogin = findViewById(R.id.AllOfPageLogin)





        val switchBtn = findViewById<Switch>(R.id.switch1)

        val textView = findViewById<TextView>(R.id.darklight)

// initialiser le mode sombre en fonction de l'état enregistré dans SharedPreferences
        val isDarkMode = MySharedPref.getBoolean(IS_DARK_MODE, false)
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchBtn.isChecked = true
            textView.text = "Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switchBtn.isChecked = false
            textView.text = "Light Mode"
        }

// set the switch to listen on checked change
        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                // sauvegarder l'état du mode sombre en tant que vrai
                MySharedPref.edit().putBoolean(IS_DARK_MODE, true).apply()
                textView.text = "Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                // sauvegarder l'état du mode sombre en tant que faux
                MySharedPref.edit().putBoolean(IS_DARK_MODE, false).apply()
                textView.text = "Light Mode"
            }
        }


    }

    fun GoToSignUp() {
        btnGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }

    fun GoToForget() {
        txtForgetPassword.setOnClickListener {
            val intent = Intent(this, ForgetPassword1::class.java).apply {}
            startActivity(intent)
            finish()
        }
    }


    fun DoActionLogin() {

        gettextwathcerlogin()
        btnLogin.setOnClickListener {
            ReadyFunction.showView(ProgressbarLogin)
            if (!Validator.VerifisEmpty(txtEmail,txtEmailLayout) or !Validator.VerifisEmpty(txtPwd,txtPasswordLayout)
                or !Validator.ValidatFormatEmail(txtEmail,txtEmailLayout)) {
                println("Something is Not Correct!")
                ReadyFunction.hideView(ProgressbarLogin)

                return@setOnClickListener
            } else {
                println("let's Go!!!!!")
                //ReadyFunction.showView(ProgressbarLogin)
                //
                viewModel.login1(txtEmail, txtPwd, this, ProgressbarLogin, this,cbRememberMe)
                //viewModel.login2(txtEmailLogin, txtPasswordLogin)
                //
                /* viewModel.getUsers()
                println("aaaaaaaaaaa " + viewModel.getUsers()!!.value)
                Log.d(TAG, viewModel.getUsers()!!.value.toString())*/
            }
        }
    }

    fun CLickToHidKeyBoard() {
        AllOfPageLogin.setOnClickListener {
           ReadyFunction.hideKeyboard(txtEmail, this@Login)
        }
    }

    private fun gettextwathcerlogin() {
        txtEmail.addTextChangedListener(EmailtxtWatcher)
        txtPwd.addTextChangedListener(PasswordtxtWatcher)
    }

    private val EmailtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtEmail,txtEmailLayout)
        }
    }
    private val PasswordtxtWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            Validator.VerifisEmpty(txtPwd,txtPasswordLayout)
        }
    }




}