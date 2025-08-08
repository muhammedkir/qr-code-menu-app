import React, { useState, useEffect, useRef } from "react";
import { Form, Button, Alert, Container, Row, Col, Card, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import '../styles/AddProductForm.css';

function AddProductForm() {
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [image, setImage] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);
  const [categories, setCategories] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const branchId = localStorage.getItem("branchId");
  const fileInputRef = useRef(null); // <-- file input'a referans


  useEffect(() => {
    const fetchCategories = async () => {
      if (!branchId) {
        setMessage("Lütfen bir şube seçin.");
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/rest/api/category/branch?branchId=${branchId}`);
        const data = await response.json();

        if (response.ok) {
          setCategories(data);
          
          if (data.length === 0) {
            alert('Ürün ekleyebilmeniz için önce bir kategori oluşturmalısınız.');
            navigate('/home/category-create');
          } else {
            setCategoryId(data[0].id);
          }
        } else {
          setMessage("Kategoriler çekilirken bir hata oluştu.");
        }
      } catch (err) {
        console.error("Kategori hatası:", err);
        setMessage("Sunucuya bağlanılamadı.");
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, [branchId, navigate]);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result);
      };
      reader.readAsDataURL(file);
    } else {
      setImagePreview(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!image) {
      setMessage("Lütfen bir görsel seçin.");
      return;
    }

    if (categories.length === 0) {
      setMessage("Önce bir kategori oluşturmalısınız.");
      return;
    }

    const formData = new FormData();
    formData.append("name", name);
    formData.append("price", price);
    formData.append("description", description);
    formData.append("categoryId", categoryId);
    formData.append("branchId", branchId);
    formData.append("image", image);

    try {
      const response = await fetch("http://localhost:8080/rest/api/product/upload", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        setMessage("✅ Ürün başarıyla eklendi.");
        setName("");
        setPrice("");
        setDescription("");
        setCategoryId("");
        setImage(null);
        setImagePreview(null);

        if (fileInputRef.current) {
          fileInputRef.current.value = null; // Dosya input'unu temizle
        }
      } else {
        setMessage("❌ Ürün eklenirken hata oluştu.");
      }
    } catch (error) {
      console.error("Sunucu hatası:", error);
      setMessage("⚠️ Sunucuya bağlanılamadı.");
    }
  };

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ height: "100vh" }}>
        <Spinner animation="border" />
      </div>
    );
  }
  
  if (categories.length === 0) {
      return (
          <Container className="mt-5 text-center">
              <h3 className="mb-4">🛍️ Ürün Ekle</h3>
              <Alert variant="warning">
                  Ürün ekleyebilmek için önce bir kategori oluşturmalısınız.
              </Alert>
              <Button onClick={() => navigate('/home/category-create')}>Kategori Oluştur</Button>
          </Container>
      );
  }

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col md={8} lg={6}>
          <Card className="shadow-lg border-0">
            <Card.Body>
              <h3 className="text-center mb-4">🛍️ Ürün Ekle</h3>
              {message && <Alert variant="info">{message}</Alert>}
              <Form onSubmit={handleSubmit} encType="multipart/form-data">
                <Form.Group className="mb-3">
                  <Form.Label>Ürün Adı</Form.Label>
                  <Form.Control
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    placeholder="Örn: Türk Kahvesi"
                  />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Fiyat (₺)</Form.Label>
                  <Form.Control
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                    min="0"
                    step="0.01"
                  />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Açıklama</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={3}
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                  />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Kategori</Form.Label>
                  <Form.Select
                    value={categoryId}
                    onChange={(e) => setCategoryId(e.target.value)}
                    required
                  >
                    <option value="" disabled>-- Kategori Seçin --</option>
                    {categories.map((cat) => (
                      <option key={cat.id} value={cat.id}>{cat.name}</option>
                    ))}
                  </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Ürün Görseli</Form.Label>
                  <Form.Control
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    required
                    ref={fileInputRef} // <-- referansı burada kullan
                  />
                </Form.Group>
                {imagePreview && (
                  <div className="mb-3 text-center">
                    <p className="fw-semibold">📷 Önizleme:</p>
                    <img
                      src={imagePreview}
                      alt="Görsel Önizleme"
                      className="img-fluid"
                      style={{ maxHeight: "300px", border: "1px solid #ccc", objectFit: "contain" }}
                    />
                  </div>
                )}
                <div className="d-grid">
                  <Button variant="success" type="submit">Ürünü Kaydet</Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default AddProductForm;