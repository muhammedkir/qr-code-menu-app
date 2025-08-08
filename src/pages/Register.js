import '../styles/Register.css';
import React, { useState } from 'react';
import { Container, Row, Col, Form, Button, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function Register() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [address, setAddress] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const [successMsg, setSuccessMsg] = useState('');
  const navigate = useNavigate();
  

  const handleRegister = async (e) => {
    e.preventDefault();
    setErrorMsg('');
    setSuccessMsg('');
    // Kayıt işlemi için backend çağrısı yap
    try {
      const response = await fetch('http://localhost:8080/rest/api/user/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password }),
      });
      if (response.ok) {
        setSuccessMsg('Kayıt başarılı! Giriş sayfasına yönlendiriliyorsunuz...');
        setTimeout(() => navigate('/login'), 2000);
      } else {
        setErrorMsg('Kayıt başarısız! Lütfen bilgilerinizi kontrol edin.');
      }
    } catch (error) {
      setErrorMsg('Bağlantı hatası!');
    }
  };

 return (
  <Container className="register-container">
    <Row className="justify-content-center">
      <Col md={6}>
        <div className="register-box">
          <h2 className="register-title">Kayıt Ol</h2>

          {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}
          {successMsg && <Alert variant="success">{successMsg}</Alert>}

          <Form onSubmit={handleRegister}>
            <Form.Group controlId="formName" className="mb-3">
              <Form.Label>Ad Soyad</Form.Label>
              <Form.Control
                type="text"
                placeholder="Adınızı ve soyadınızı girin"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </Form.Group>

            <Form.Group controlId="formEmail" className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="Email adresinizi girin"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </Form.Group>

            <Form.Group controlId="formPassword" className="mb-4">
              <Form.Label>Şifre</Form.Label>
              <Form.Control
                type="password"
                placeholder="Şifrenizi girin"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </Form.Group>

            <Form.Group controlId="fromPhoneNumber" className="mb-4">
              <Form.Label>Telefon Numarası</Form.Label>
              <Form.Control
                type="tel" // <- `type="tel"` ile mobil klavye açılır
                placeholder="5xx xxx xx xx"
                value={phoneNumber}
                onChange={(e) => {
                  // Sadece sayıları al, 10 haneye sınırla
                  const value = e.target.value.replace(/\D/g, '').slice(0, 10);
                  setPhoneNumber(value);
                }}
                pattern="[0-9]{10}" // HTML5 doğrulama kuralı
                title="Telefon numarası 10 haneli olmalıdır."
                required
              />
            </Form.Group>

            <Form.Group controlId="formAddress" className="mb-4">
              <Form.Label>Adres</Form.Label>
              <Form.Control
                type="text"
                placeholder="Adresinizi girin"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                required
              />
            </Form.Group>

            <Button variant="success" type="submit" className="register-btn">
              Kayıt Ol
            </Button>
          </Form>
        </div>
      </Col>
    </Row>
  </Container>
);
}

export default Register;
