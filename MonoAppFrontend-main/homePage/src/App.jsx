import ReCAPTCHA from "react-google-recaptcha";
import React, { useState, useEffect } from "react";
import "./App.css";

function HomePage() {
  const [showLogin, setShowLogin] = useState(false);
  const [showRegister, setShowRegister] = useState(false);

  // Captcha: tracks if the user has successfully solved the challenge
  const [captchaVerified, setCaptchaVerified] = useState(false);

  const [loadingLogin, setLoadingLogin] = useState(false);
  const [loadingRegister, setLoadingRegister] = useState(false);

  // Login states
  const [loginMail, setLoginMail] = useState("");
  const [loginPassword, setLoginPassword] = useState("");
  const [loginError, setLoginError] = useState("");

  // Register states
  const [regName, setRegName] = useState("");
  const [regMail, setRegMail] = useState("");
  const [regPassword, setRegPassword] = useState("");
  const [regCigInitial, setRegCigInitial] = useState("");
  const [regCigPrice, setRegCigPrice] = useState("");
  const [registerError, setRegisterError] = useState("");

  // Motivational quotes
  const [quoteIndex, setQuoteIndex] = useState(0);
  const [fade, setFade] = useState(true);

  const quotes = [
    "Every cigarette you don’t smoke is a small victory. These victories add up, building the strength and confidence you need to break free from addiction once and for all.",
    "Quitting smoking is not about perfection but persistence. Even on the hardest days, choosing not to smoke brings you closer to the healthier, stronger, freer version of yourself.",
    "Your body heals a little more every day you stay away from cigarettes. What feels small now becomes something monumental when you look back.",
    "The urge to smoke will pass. The pride of not giving in will stay with you forever. You are building a future where you control your life, not nicotine.",
    "There will be moments when quitting feels impossible, but those moments are the same ones that make you stronger. Every time you resist, you weaken the addiction’s grip.",
    "Choosing not to smoke today means choosing your future — your health, your energy, your money, and your freedom. You deserve all of it.",
    "Every craving lasts minutes. Every benefit of quitting lasts a lifetime. You have more strength than you think.",
    "You’re not just quitting smoking — you’re reclaiming your life. Every step forward, no matter how small, is a step toward a healthier, happier you.",
  ];

  // Rotate quotes periodically with a fade animation
  useEffect(() => {
    const interval = setInterval(() => {
      setFade(false);
      setTimeout(() => {
        setQuoteIndex((prev) => (prev + 1) % quotes.length);
        setFade(true);
      }, 400);
    }, 7000);

    return () => clearInterval(interval);
  }, []);

  // Open login modal and reset login-related errors
  const openLogin = () => {
    setShowLogin(true);
    setShowRegister(false);
    setLoginError("");
    setCaptchaVerified(false);
  };

  // Open register modal and reset register-related errors
  const openRegister = () => {
    setShowRegister(true);
    setShowLogin(false);
    setRegisterError("");
    setCaptchaVerified(false);
  };

  // Close both modals and reset shared state
  const closeModals = () => {
    setShowLogin(false);
    setShowRegister(false);
    setCaptchaVerified(false);
    setLoginError("");
    setRegisterError("");
  };

  // ----------------------
  // LOGIN SUBMIT HANDLER
  // ----------------------
  // Sends login credentials to the backend API.
  // If the response contains a token (token/accessToken), redirects to the main app.
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    setLoginError("");
    setLoadingLogin(true);

    try {
      const res = await fetch("https://monoapp.onrender.com/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          mail: loginMail,
          password: loginPassword,
        }),
      });

      console.log("Login status:", res.status);

      if (!res.ok) {
        setLoginError("Incorrect email or password");
        return;
      }

      // Handle JSON or plain-text responses
      let raw;
      let data;

      try {
        raw = await res.json();
        data = raw;
      } catch {
        const text = await res.text();
        raw = text;
        data = { token: text.trim() };
      }

      console.log("Login response:", raw);

      // Accept several possible token field names
      let token =
        data.token ||
        data.accessToken ||
        data.access_token ||
        (typeof data === "string" ? data.trim() : null);

      if (!token) {
        setLoginError("Login failed. No token received.");
        return;
      }

      // Redirect user to the main application with token in query string
      window.location.href =
        "https://mono-app-inpage.vercel.app/?token=" + encodeURIComponent(token);
    } catch (err) {
      console.error("Login error:", err);
      setLoginError("Network error. Please try again.");
    } finally{
      setLoadingLogin(false);
    }
  };

  // ----------------------
  // REGISTER SUBMIT HANDLER
  // ----------------------
  // Sends registration data to the backend API.
  // If the response contains a token (token/accessToken), redirects to the main app.
  const handleRegisterSubmit = async (e) => {
    e.preventDefault();
    setRegisterError("");
    setLoadingRegister(true);

    try {
      const res = await fetch(
        "https://monoapp.onrender.com/api/auth/register",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            name: regName,
            mail: regMail,
            password: regPassword,
            cigInitial: Number(regCigInitial),
            cigPrice: Number(regCigPrice),
          }),
        }
      );

      console.log("Register status:", res.status);

      if (!res.ok) {
        setRegisterError("Could not create the account. Please try again.");
        return;
      }

      // Handle JSON or plain-text responses
      let raw;
      let data;
      try {
        raw = await res.json();
        data = raw;
      } catch {
        const text = await res.text();
        raw = text;
        data = { token: text.trim() };
      }

      console.log("Register raw response:", raw);

      // Accept several possible token field names
      let token =
        data.token ||
        data.accessToken ||
        data.access_token ||
        (typeof data === "string" ? data.trim() : null);

      if (!token) {
        setRegisterError("Account created but no token received.");
        return;
      }

      // Redirect user to the main application (local dev URL here)
      window.location.href =
        "http://localhost:5173/?token=" + encodeURIComponent(token);
    } catch (err) {
      console.error("Register error:", err);
      setRegisterError("Network error. Please try again.");
    } finally {
      setLoadingRegister(false);
    }
  };

  return (
    <div className="homepage">
      {/* HEADER */}
      <header className="header">
        <div className="logo">MonoApp</div>

        <div className="auth-buttons">
          <button className="btn btn-login" onClick={openLogin}>
            Login
          </button>
          <button className="btn btn-register" onClick={openRegister}>
            Register
          </button>
        </div>
      </header>

      {/* MAIN CONTENT */}
      <main className="main-content">
        <h1>Quit smoking, one day at a time.</h1>
        <p className="subtitle">
          Track your progress, stay motivated, and save money while you leave
          cigarettes behind.
        </p>

        <div className="quote-box">
          <p className={`quote-text ${fade ? "fade-in" : "fade-out"}`}>
            {quotes[quoteIndex]}
          </p>
        </div>
      </main>

      {/* LOGIN MODAL */}
      {showLogin && (
        <div className="modal-overlay" onClick={closeModals}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <button className="modal-close" onClick={closeModals}>
              ×
            </button>
            <h2>Login</h2>

            <form className="modal-form" onSubmit={handleLoginSubmit}>
              <label>
                Email
                <input
                  type="email"
                  value={loginMail}
                  onChange={(e) => setLoginMail(e.target.value)}
                  placeholder="Enter your email"
                  required
                />
              </label>

              <label>
                Password
                <input
                  type="password"
                  value={loginPassword}
                  onChange={(e) => setLoginPassword(e.target.value)}
                  placeholder="Enter your password"
                  required
                />
              </label>

              {loginError && <p className="error-text">{loginError}</p>}

              {/* Captcha must be solved before enabling the Login button */}
              <ReCAPTCHA
                sitekey="6LeS4RssAAAAACu4KRNjBji-Aw_TDFDpfjlhvjSW"
                onChange={() => setCaptchaVerified(true)}
              />

              <button
                type="submit"
                className="btn btn-primary"
                disabled={!captchaVerified || loadingLogin}
              >
                {loadingLogin ? (
                  <div className="spinner"></div>
                ) : (
                  "Login"
                )}
              </button>
            </form>

            <p className="switch-text">
              Don't have an account?{" "}
              <button className="link-button" onClick={openRegister}>
                Create one
              </button>
            </p>
          </div>
        </div>
      )}

      {/* REGISTER MODAL */}
      {showRegister && (
        <div className="modal-overlay" onClick={closeModals}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <button className="modal-close" onClick={closeModals}>
              ×
            </button>
            <h2>Create account</h2>

            <form className="modal-form" onSubmit={handleRegisterSubmit}>
              <label>
                Name
                <input
                  type="text"
                  value={regName}
                  onChange={(e) => setRegName(e.target.value)}
                  placeholder="Enter your name"
                  required
                />
              </label>

              <label>
                Email
                <input
                  type="email"
                  value={regMail}
                  onChange={(e) => setRegMail(e.target.value)}
                  placeholder="Enter your email"
                  required
                />
              </label>

              <label>
                Password
                <input
                  type="password"
                  value={regPassword}
                  onChange={(e) => setRegPassword(e.target.value)}
                  placeholder="Create a password"
                  required
                />
              </label>

              <label>
                Cigarettes per day (avg.)
                <input
                  type="number"
                  value={regCigInitial}
                  onChange={(e) => setRegCigInitial(e.target.value)}
                  placeholder="e.g. 8"
                  required
                />
              </label>

              <label>
                Cigarette pack price (€)
                <input
                  type="number"
                  step="0.1"
                  value={regCigPrice}
                  onChange={(e) => setRegCigPrice(e.target.value)}
                  placeholder="e.g. 5.5"
                  required
                />
              </label>

              {registerError && (
                <p className="error-text">{registerError}</p>
              )}

              {/* Captcha must be solved before enabling the Register button */}
              <ReCAPTCHA
                sitekey="6LeS4RssAAAAACu4KRNjBji-Aw_TDFDpfjlhvjSW"
                onChange={() => setCaptchaVerified(true)}
              />

              <button
                type="submit"
                className="btn btn-primary"
                disabled={!captchaVerified || loadingRegister}
              >
                {loadingRegister ? (
                  <div className="spinner"></div>
                ) : (
                  "Register"
                )}
              </button>
            </form>

            <p className="switch-text">
              Already have an account?{" "}
              <button className="link-button" onClick={openLogin}>
                Login
              </button>
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default HomePage;