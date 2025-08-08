import React, { useState } from 'react';
import { Form, Button, Container, Alert,OverlayTrigger, Tooltip } from 'react-bootstrap';

function CreateBranchPage() {
  const [form, setForm] = useState({
    name: '',
    address: '',
    phoneNumber: '',
    restaurantCode: '',
  });

  const [logoFile, setLogoFile] = useState(null);
  const [logoPreview, setLogoPreview] = useState(null); // 👈 Önizleme için
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    // Telefon numarası için sadece rakam kontrolü
    if (name === 'phoneNumber') {
      const numericValue = value.replace(/\D/g, ''); // Sadece rakamları al
      if (numericValue.length <= 10) { // 10 haneli sınırı
        setForm({
          ...form,
          [name]: numericValue
        });
      }
    } else {
      setForm({
        ...form,
        [name]: value
      });
    }
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setLogoFile(file);
    if (file) {
      setLogoPreview(URL.createObjectURL(file)); // 👈 Geçici URL oluştur
    } else {
      setLogoPreview(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage('');
    setErrorMessage('');

    // userId'yi localStorage'dan al
    const userId = localStorage.getItem("userId");
    if (!userId) {
      setErrorMessage('Kullanıcı bilgisi bulunamadı. Lütfen tekrar giriş yapın.');
      return;
    }

    const formData = new FormData();
    formData.append("name", form.name);
    formData.append("address", form.address);
    formData.append("phoneNumber", form.phoneNumber);
    formData.append("restaurantCode", form.restaurantCode);
    formData.append("userId", userId); // userId'yi ekle
    if (logoFile) {
      formData.append("image", logoFile);
    }

    try {
      const response = await fetch("http://localhost:8080/rest/api/branch/upload", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        setSuccessMessage('Şube başarıyla oluşturuldu!');
        setForm({
          name: '',
          address: '',
          phoneNumber: '',
          restaurantCode: '',
        });
        setLogoFile(null);
        setLogoPreview(null); // 👈 Önizlemeyi temizle
        // Dosya inputunu sıfırla
        const fileInput = document.getElementById('formLogoFile');
        if (fileInput) {
          fileInput.value = '';
        }
      } else {
        const err = await response.text();
        setErrorMessage(`Hata: ${err}`);
      }
    } catch (err) {
      setErrorMessage('İstek gönderilirken hata oluştu.');
    }
  };

  return (
    <Container className="mt-4">
      <h2>Şube Oluştur</h2>

      {successMessage && <Alert variant="success">{successMessage}</Alert>}
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Şube Adı</Form.Label>
          <Form.Control
            type="text"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Adres</Form.Label>
          <Form.Control
            type="text"
            name="address"
            value={form.address}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Telefon</Form.Label>
          <Form.Control
            type="text"
            name="phoneNumber"
            value={form.phoneNumber}
            onChange={handleChange}
            placeholder="5*********"
            maxLength="10"
            pattern="[0-9]{10}"
            required
          />
        </Form.Group>

        <Form.Group controlId="formRestaurantCode" className="mb-3">
          <Form.Label className="d-flex align-items-center">
            Restaurant Kodu
            <OverlayTrigger
              placement="right"
              overlay={
                <Tooltip id="tooltip-restaurant-code">
                  Restoranınıza özel kısa bir kod girin. Bu kod, QR menü linkinizde kullanılacaktır.
                </Tooltip>
              }
            >
              <i className="bi bi-question-circle ms-2" style={{ cursor: 'pointer' }}></i>
            </OverlayTrigger>
          </Form.Label>
          <Form.Control
            type="text"
            name="restaurantCode"
            placeholder="Restoran kodunu girin"
            value={form.restaurantCode}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Form.Group controlId="formLogoFile" className="mb-3">
          <Form.Label>Şube Logosu</Form.Label>
          <Form.Control
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            required
          />
        </Form.Group>

        {logoPreview && (
          <div className="mb-3">
            <p><strong>Seçilen Logo Önizlemesi:</strong></p>
            <img src={logoPreview} alt="Logo Preview" style={{ maxWidth: '200px', height: 'auto' }} />
          </div>
        )}

        <Button variant="primary" type="submit">
          Oluştur
        </Button>
      </Form>
    </Container>
  );
}

export default CreateBranchPage;
