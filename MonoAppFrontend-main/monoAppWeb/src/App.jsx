import { useState, useEffect, useRef } from "react";
import "./App.css";
import monkeyImage from "./assets/mono.png";
import happyMon from "./assets/happy_mon.png";     //  mono feliz
import sadMon from "./assets/sad_mon.png";         //  mono triste

// 👉 baseURL de tu backend
const baseURL = "https://monoapp.onrender.com";

function App() {
  const [count, setCount] = useState(0);
  const [maxCigarettes, setMaxCigarettes] = useState(8);
  const [limitDraft, setLimitDraft] = useState(10);

  const [showSettings, setShowSettings] = useState(false);
  const [activeTab, setActiveTab] = useState("mono");

  const [isBumping, setIsBumping] = useState(false);
  const [isPulsing, setIsPulsing] = useState(false);

  const [darkMode, setDarkMode] = useState(false);

  const [homePhase, setHomePhase] = useState("idle");
  const [showBreathPopup, setShowBreathPopup] = useState(false);
  const inhaleTimeoutRef = useRef(null);
  const exhaleTimeoutRef = useRef(null);

  const [savedAmount, setSavedAmount] = useState(0);
  const [isAnimatingAmount, setIsAnimatingAmount] = useState(false);
  const amountIntervalRef = useRef(null);

  const [userEmail, setUserEmail] = useState("");
  const [userName, setUserName] = useState("");
  const [userId, setUserId] = useState(null);

  const [showMascot, setShowMascot] = useState(false);

  // 💰 dinero total ahorrao en BD (de /api/savings/{userId}/money-saved)
  const [totalMoneySaved, setTotalMoneySaved] = useState(0);

  const medicalPhrases = [
    "Smoking damages your lungs and increases the risk of chronic bronchitis and emphysema.",
    "Within 20 minutes of not smoking, your heart rate and blood pressure start to drop.",
    "After 24 hours without cigarettes, carbon monoxide levels in your blood drop and oxygen levels improve.",
    "Quitting smoking reduces your risk of heart attack and stroke, no matter how long you’ve smoked.",
    "Every cigarette harms the tiny air sacs in your lungs that you need to breathe and exercise.",
    "Smokers are more likely to get severe infections like pneumonia and flu complications.",
    "Stopping smoking improves your circulation, so more oxygen reaches your brain and muscles.",
    "Quitting now protects people around you from secondhand smoke, especially children.",
    "Within a few weeks of quitting, many people notice less coughing and easier breathing.",
    "Every day smoke-free is a day your body is actively repairing damage."
  ];

  const totalCigarettesAvoided = 1100;

  const isOverLimit = count > maxCigarettes;

  const safeCount = Math.min(count, maxCigarettes);
  const progressPercent = maxCigarettes > 0 ? (safeCount / maxCigarettes) * 100 : 0;

  // 🔐 login + carga de usuario + ahorro desde BD
  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const tokenFromUrl = params.get("token");

    // si no hay token ni en la url ni en localStorage → mandar al homepage
    if (!tokenFromUrl && !localStorage.getItem("authToken")) {
      window.location.href = "https://mono-app-homepage.vercel.app/";
      return;
    }

    if (tokenFromUrl) {
      localStorage.setItem("authToken", tokenFromUrl);
      const clean = window.location.origin + window.location.pathname;
      window.history.replaceState({}, "", clean);
    }

    const token = tokenFromUrl || localStorage.getItem("authToken");
    if (!token) return;

    const fetchUserAndSavings = async () => {
      try {
        // 1) datos del usuario
        const res = await fetch(`${baseURL}/api/users/me`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });

        const data = await res.json().catch(() => null);

        if (res.ok && data) {
          setUserName(data.name || "");
          setUserEmail(data.mail || "");

          if (data.id) {
            setUserId(data.id);
          }

          // límite de cigarros inicial desde BD (cigInitial)
          if (typeof data.cigInitial === "number") {
            setMaxCigarettes(data.cigInitial);
            setLimitDraft(data.cigInitial);
          }

          // 2) ahorro total desde /api/savings/{userId}/money-saved
          const uid = data.id;
          if (uid) {
            try {
              const savingsRes = await fetch(
                `${baseURL}/api/savings/${uid}/money-saved`,
                {
                  method: "GET",
                  headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                  },
                }
              );

              const savingsData = await savingsRes.json().catch(() => null);

              // puede ser número directo o objeto
              if (savingsRes.ok && savingsData !== null) {
                if (typeof savingsData === "number") {
                  setTotalMoneySaved(savingsData);
                } else if (typeof savingsData.moneySaved === "number") {
                  setTotalMoneySaved(savingsData.moneySaved);
                }
              }
            } catch (err) {
              console.error("Error fetching savings:", err);
            }
          }
        }
      } catch (err) {
        console.error("Error fetching user:", err);
      }
    };

    fetchUserAndSavings();
  }, []);

  // animacione
  useEffect(() => {
    if (!isBumping) return;
    const t = setTimeout(() => setIsBumping(false), 200);
    return () => clearTimeout(t);
  }, [isBumping]);

  useEffect(() => {
    if (!isOverLimit) return;
    setIsPulsing(true);
    const t = setTimeout(() => setIsPulsing(false), 250);
    return () => clearTimeout(t);
  }, [count, isOverLimit]);

  useEffect(() => {
    return () => {
      clearTimeout(inhaleTimeoutRef.current);
      clearTimeout(exhaleTimeoutRef.current);
      clearInterval(amountIntervalRef.current);
    };
  }, []);

  const handleAdd = () => {
    setCount((p) => p + 1);
    setIsBumping(true);
  };

  const handleRemove = () => setCount((p) => Math.max(p - 1, 0));

  const handleNewDayClick = () => {
    setCount((prev) => {
      const previousCount = prev;
  
      const token = localStorage.getItem("authToken");
      if (token && userId) {
        const API_URL = `${baseURL}/api/savings/${userId}/money-saved`; 
        // 👆 cambia esta ruta si tu backend usa otra específica para guardar este evento
  
        fetch(API_URL, {
          method: "POST",              // o el método que montéis en backend
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            newCount: 0,              // el cero que has pedido
            smokedToday: previousCount, // la cantidad que llevaba fumada antes de resetear
          }),
        }).catch((err) => {
          console.error("Error sending day reset:", err);
        });
      }
  
      // reseteamos el contador en la UI
      return 0;
    });
  };

  const openSettings = () => {
    setLimitDraft(maxCigarettes);
    setShowSettings(true);
    setActiveTab("settings");
  };

  // guardar nuevo límite en BD: {{baseURL}}/api/users/cig-initial
  const handleSaveLimit = async () => {
    const num = parseInt(limitDraft, 10);

    if (isNaN(num) || num <= 0) {
      alert("Please enter a valid number greater than 0");
      return;
    }

    setMaxCigarettes(num);
    setShowSettings(false);
    setActiveTab("mono");

    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("No auth token found, cannot sync with server");
      return;
    }

    const API_METHOD = "PATCH"; // cambia a PUT/POST si tu backend lo necesita
    const API_URL = `${baseURL}/api/users/cig-initial`;

    try {
      const res = await fetch(API_URL, {
        method: API_METHOD,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          cigInitial: num,
        }),
      });

      if (!res.ok) {
        const text = await res.text();
        console.error(
          `Error updating cigInitial (${API_METHOD} ${API_URL}):`,
          res.status,
          text
        );
      }
    } catch (err) {
      console.error("Request failed:", err);
    }
  };

  // ciclo de respiración
  const handleHomeButtonClick = () => {
    if (homePhase !== "idle") return;

    setShowBreathPopup(false);
    setHomePhase("inhale");

    inhaleTimeoutRef.current = setTimeout(() => {
      setHomePhase("exhale");

      exhaleTimeoutRef.current = setTimeout(() => {
        setHomePhase("idle");
        setShowBreathPopup(true);
      }, 5000);
    }, 5000);
  };

  const [quoteIndex, setQuoteIndex] = useState(0);

  const handleNewMedicalPhrase = () => {
    setQuoteIndex((prev) => {
      let next = prev;
      while (next === prev) next = Math.floor(Math.random() * medicalPhrases.length);
      return next;
    });
  };

  return (
    <div className={`app-root ${darkMode ? "dark-theme" : ""}`}>
      <div className="phone-frame">
        <div className="app-container">

          {/* HEADER */}
          <div className="top-header">
          <p className="top-header-welcome">Welcome {userName || "..."}.</p>
          <div className="top-header-box">
            <p className="top-header-text">Your smoke-free journey begins today</p>
          </div>

          <p
            className="top-header-subtext"
            onClick={handleNewDayClick}
          >
            Click to start a new day
          </p>
        </div>

          {/* ===================== MONO TAB ===================== */}
          {activeTab === "mono" && (
            <div className="screen">

              {/* ===== CONTADOR ===== */}
              <div className="counter-card">
                <h2 className="counter-title">Cigarette</h2>

                <div className="progress-bar">
                  <div
                    className={
                      "progress-fill" +
                      (isBumping ? " progress-bump" : "") +
                      (isOverLimit ? " over-limit-fill" : "") +
                      (isOverLimit && isPulsing ? " over-limit-pulse" : "")
                    }
                    style={{ width: `${progressPercent}%` }}
                  />
                </div>

                <p className={"counter-text" + (isOverLimit ? " over-limit-text" : "")}>
                  {count} / {maxCigarettes}
                </p>

                <div className="button-row">
                  <button className="counter-button" onClick={handleAdd}>
                    Add cigarette
                  </button>
                  <button className="remove-button" onClick={handleRemove}>
                    ✕
                  </button>
                </div>
              </div>

              {/* ⭐ MASCOTA DEBAJO DEL CONTADOR ⭐ */}
              <div
                className="mascot-toggle"
                onClick={() => setShowMascot(true)}
              >
                {!showMascot && (
                  <p className="mascot-text">CLICK ON ME TO SEE THE MONO.</p>
                )}

                {showMascot && (
                  <img
                    src={isOverLimit ? sadMon : happyMon}
                    alt="Mascot"
                    className="mascot-image mascot-bounce"
                  />
                )}
              </div>
            </div>
          )}

          {/* ===================== HOME TAB ===================== */}
          {activeTab === "home" && (
            <div className="screen">

              <div className="home-center">
                <p className="dont-smoke">Do you want to smoke? Try this first</p>

                <button
                  className={`home-button ${
                    homePhase === "inhale" ? "home-button-inhale" :
                    homePhase === "exhale" ? "home-button-exhale" : ""
                  }`}
                  onClick={handleHomeButtonClick}
                  disabled={homePhase !== "idle"}
                >
                  <span className="home-button-label">
                    {homePhase === "inhale"
                      ? "Keep going..."
                      : homePhase === "exhale"
                      ? "Almost done..."
                      : "I didn't smoke"}
                  </span>
                  <span className="home-button-fill" />
                </button>

                {homePhase === "inhale" && <p className="home-breath-label">Inhale</p>}
                {homePhase === "exhale" && <p className="home-breath-label">Exhale</p>}
              </div>

              <div className="home-quote-box">
                <p className="home-quote-text">{medicalPhrases[quoteIndex]}</p>
                <button className="home-quote-button" onClick={handleNewMedicalPhrase}>
                  New fact
                </button>
              </div>

            </div>
          )}

          {/* ====================== NAV BAR ====================== */}
          <nav className="bottom-nav">
            <button
              className={`nav-button ${activeTab === "mono" ? "nav-active" : ""}`}
              onClick={() => setActiveTab("mono")}
            >
              🐒
            </button>
            <button
              className={`nav-button ${activeTab === "home" ? "nav-active" : ""}`}
              onClick={() => setActiveTab("home")}
            >
              🏠
            </button>
            <button
              className={`nav-button ${activeTab === "settings" ? "nav-active" : ""}`}
              onClick={openSettings}
            >
              ⚙️
            </button>
          </nav>

          {/* ====================== SETTINGS ====================== */}
          {showSettings && (
            <div className="settings-overlay">
              <div className="settings-modal">
                <h2 className="settings-title">Settings</h2>

                <div className="settings-section">
                  <p className="settings-label">Email</p>
                  <p className="settings-value">{userEmail || "user@example.com"}</p>
                </div>

                <div className="settings-section">
                  <p className="settings-label">Daily cigarettes limit</p>
                  <input
                    type="number"
                    min="1"
                    className="settings-input"
                    value={limitDraft}
                    onChange={(e) => setLimitDraft(e.target.value)}
                  />
                </div>

                <div className="settings-section settings-row">
                  <p className="settings-label">Dark mode</p>
                  <label className="toggle">
                    <input
                      type="checkbox"
                      checked={darkMode}
                      onChange={(e) => setDarkMode(e.target.checked)}
                    />
                    <span className="toggle-slider" />
                  </label>
                </div>

                <div className="settings-section">
                  <p className="settings-label">Your progress</p>
                  <p className="settings-progress-line">
                    Total money saved: <span>€{totalMoneySaved}</span>
                  </p>
                  <p className="settings-progress-line">
                    Cigarettes avoided: <span>{totalCigarettesAvoided}</span>
                  </p>
                </div>

                <div className="settings-actions">
                  <button
                    className="settings-secondary-button"
                    onClick={() => {
                      setShowSettings(false);
                      setActiveTab("mono");
                    }}
                  >
                    Cancel
                  </button>

                  <button className="settings-primary-button" onClick={handleSaveLimit}>
                    Save changes
                  </button>
                </div>
              </div>
            </div>
          )}

          {/* ====================== BREATH POPUP ====================== */}
          {showBreathPopup && (
            <div className="breath-overlay">
              <div className="breath-modal">
                <p className="breath-title">Congrats</p>
                <p className="breath-text">You just saved 0.5 cents.</p>

                <p className="breath-subtext">
                  <b><u>Still wanting to smoke?</u></b> Click the button, it will show how much
                  <strong> money you have saved </strong> until you use this WebApp.
                </p>

                {savedAmount > 0 && (
                  <p className="breath-amount">
                    {savedAmount}
                    <span className="breath-amount-currency">€</span>
                  </p>
                )}

                <button
                  className="breath-button"
                  onClick={() => {
                    if (isAnimatingAmount) return;

                    setIsAnimatingAmount(true);
                    setSavedAmount(0);

                    // 🎯 objetivo: valor de BD de /api/savings/{userId}/money-saved
                    const target = totalMoneySaved || 0;
                    const duration = 2000;
                    const steps = 40;
                    let step = 0;

                    if (amountIntervalRef.current)
                      clearInterval(amountIntervalRef.current);

                    const intervalId = setInterval(() => {
                      step += 1;
                      const progress = Math.min(step / steps, 1);
                      const value = Math.round(progress * target);
                      setSavedAmount(value);

                      if (progress === 1) {
                        clearInterval(intervalId);
                        amountIntervalRef.current = null;
                        setIsAnimatingAmount(false);
                      }
                    }, duration / steps);

                    amountIntervalRef.current = intervalId;
                  }}
                  disabled={isAnimatingAmount}
                >
                  {isAnimatingAmount ? "Calculating..." : "Show total saved"}
                </button>

                <button
                  className="breath-button breath-button-secondary"
                  onClick={() => {
                    setShowBreathPopup(false);
                    setSavedAmount(0);

                    if (amountIntervalRef.current) {
                      clearInterval(amountIntervalRef.current);
                      amountIntervalRef.current = null;
                    }

                    setIsAnimatingAmount(false);
                  }}
                >
                  Close
                </button>

              </div>
            </div>
          )}

        </div>
      </div>
    </div>
  );
}

export default App;