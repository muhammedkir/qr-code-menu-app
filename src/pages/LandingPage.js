import React from "react";
import { Button,Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Landingİmage from "../assets/Landingİmage.jpg";
import "../styles/LandingPage.css"; // Özel stiller için CSS dosyasını dahil et

const LandingPage = () => {
  const navigate = useNavigate();

  return (
    <div className="landing-container d-flex flex-column flex-md-row align-items-center justify-content-center p-4">
      {/* Sol Taraftaki Yazı ve Butonlar Bölümü */}
      <Col md={6} className="text-section d-flex flex-column justify-content-center">
        <h1 className="display-4 fw-bold mb-3 animate-slide-in"> {/* Animasyon sınıfı eklendi */}
          Cafe ve Restaurantlar için Kolay Yönetim Sistemi
        </h1>
        <p className="lead text-muted highlight animate-slide-in" style={{ animationDelay: '0.2s' }}> {/* Gecikme eklendi */}
          Anlık <strong>Stok yönetimi</strong> ile güçlendirin ve <strong>Sınırsız</strong> Rapor Alın
        </p>
        <p className="text-muted animate-slide-in" style={{ animationDelay: '0.4s' }}> {/* Gecikme eklendi */}
          Sipariş yönetimi, envanter takibi ve müşteri işlemleri gibi süreçleri kolayca yönetin.
        </p>
        <div className="button-group mt-4 d-flex gap-2 animate-slide-in" style={{ animationDelay: '0.6s' }}> {/* Gecikme eklendi */}
          <Button variant="primary" size="lg" onClick={() => navigate("/login")}>
            Giriş Yap
          </Button>
          <Button variant="outline-primary" size="lg" onClick={() => navigate("/register")}>
            Kayıt Ol
          </Button>
        </div>
      </Col>

      {/* Sağ Taraftaki Görsel ve Tanıtım Bölümü */}
      <div className="image-section text-center mt-5 mt-md-0 ms-md-5">
        <h2 className="display-6 fw-bold mb-3 text-secondary">
          QR Menü Sistemine Hoş Geldiniz
        </h2>
        <img
          src={Landingİmage}
          alt="Restoran yönetim sistemi"
          className="img-fluid rounded-4 shadow-lg"
        />
        <p className="mt-4 lead text-muted">
          <strong>Akıllı restoran deneyimi için QR menümüzle siparişlerinizi kolayca yönetin.</strong>
        </p>
      </div>
    </div>
  );
};

export default LandingPage;