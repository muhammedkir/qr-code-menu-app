import '../styles/Login.css';
import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const [loading, setLoading] = useState(false); // Yüklenme durumu için state
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMsg('');

    try {
      const response = await fetch('http://localhost:8080/rest/api/user/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const user = await response.json();
        localStorage.setItem('userId', user.id);
        localStorage.setItem('username', user.username || user.email);
        navigate('/home');
      } else {
        const errorData = await response.json();
        setErrorMsg(errorData.message || 'Giriş başarısız oldu.');
      }
    } catch (error) {
      setErrorMsg('Sunucu hatası veya ağ bağlantı sorunu.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page-bg d-flex justify-content-center align-items-center">
      <Container>
        <Row className="justify-content-center">
          <Col md={6} lg={4}>
            <div className="login-card">
              <h2 className="login-title">Giriş Yap</h2>
              {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}
              <Form onSubmit={handleLogin}>
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
                <Button variant="primary" type="submit" className="w-100" disabled={loading}>
                  {loading ? 'Yükleniyor...' : 'Giriş Yap'}
                </Button>
              </Form>
              <div className="text-center mt-3">
                <span className="text-muted">Hesabınız yok mu? </span>
                <Button variant="link" onClick={() => navigate("/register")}>
                  Kayıt Ol
                </Button>
              </div>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default Login;