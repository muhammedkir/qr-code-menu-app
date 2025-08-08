import React, { useState, useRef } from "react";
import { Form, Button, Alert, Container, Row, Col, Card } from "react-bootstrap";

function CategoryCreateForm() {
  const [name, setName] = useState("");
  const [image, setImage] = useState(null);
  const [preview, setPreview] = useState(null);
  const [message, setMessage] = useState("");
  const branchId = localStorage.getItem("branchId");
  const fileInputRef = useRef(null); // Referans oluştur

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);

    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => setPreview(reader.result);
      reader.readAsDataURL(file);
    } else {
      setPreview(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!name || !image) {
      setMessage("Lütfen kategori adı ve görsel seçin.");
      return;
    }

    const formData = new FormData();
    formData.append("name", name);
    formData.append("branchId", branchId);
    formData.append("image", image);

    try {
      const response = await fetch("http://localhost:8080/rest/api/category/upload", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        setMessage("✅ Kategori başarıyla oluşturuldu.");
        setName("");
        setImage("");
        setPreview("");

        if (fileInputRef.current) {
          fileInputRef.current.value = null; // Dosya input'unu temizle
        }
      } else {
        setMessage("❌ Kategori eklenemedi.");
      }
    } catch (err) {
      console.error("Kategori yükleme hatası:", err);
      setMessage("⚠️ Sunucuya ulaşılamadı.");
    }
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col md={8} lg={6}>
          <Card className="shadow-lg border-0">
            <Card.Body>
              <h4 className="text-center mb-4">📁 Yeni Kategori Oluştur</h4>

              {message && <Alert variant="info">{message}</Alert>}

              <Form onSubmit={handleSubmit} encType="multipart/form-data">
                <Form.Group className="mb-3">
                  <Form.Label>Kategori Adı</Form.Label>
                  <Form.Control
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    placeholder="Örn: Tatlılar"
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Kategori Görseli</Form.Label>
                  <Form.Control
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    required
                    ref={fileInputRef} // Dosya input'a referans
                  />
                </Form.Group>

                {preview && (
                  <div className="mb-3 text-center">
                    <p className="fw-semibold">📷 Önizleme:</p>
                    <img
                      src={preview}
                      alt="Görsel Önizleme"
                      className="img-fluid"
                      style={{ maxHeight: "300px", border: "1px solid #ccc", objectFit: "contain" }}
                    />
                  </div>
                )}

                <div className="d-grid">
                  <Button variant="success" type="submit">Kaydet</Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default CategoryCreateForm;
