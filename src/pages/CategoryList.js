import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Card, Button, Form, Modal,} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';



function CategoryList() {
  const [categories, setCategories] = useState([]);
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [updatedCategoryName, setUpdatedCategoryName] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const branchId = localStorage.getItem('branchId');
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchCategories() {

      // Eğer branchId yoksa, fetching işlemini durdur
      if (!branchId) {
        setError('Lütfen bir şube seçin.');
        setLoading(false);
        // İsterseniz kullanıcıyı şube seçme sayfasına yönlendirebilirsiniz
        navigate('/home/branch-list');
        return;
      }

      try {
        const res = await fetch(`http://localhost:8080/rest/api/category/branch?branchId=${branchId}`);
        if (!res.ok) throw new Error('Kategoriler getirilemedi');
        const data = await res.json();

        if (Array.isArray(data)) {
          setCategories(data);
        } else {
          throw new Error('Sunucudan beklenmeyen veri geldi');
        }
       
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    } 
      fetchCategories();
  }, [branchId]);


  const openDetails = (category) => {
    setSelectedCategory(category);
    setUpdatedCategoryName(category.name);
    setShowDetailsModal(true);
  };

  const handleUpdateCategory = async () => {
    if (!updatedCategoryName.trim() || !selectedCategory) return;
    if (!branchId) {
        alert('Şube bilgisi bulunamadı, kategori güncellenemez.');
        return;
    }

    try {
      const res = await fetch(`http://localhost:8080/rest/api/category/update/${selectedCategory.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: updatedCategoryName,
          branchId: selectedCategory.branchId
        }),
      });


      if (!res.ok) throw new Error('Kategori güncellenemedi');

      setCategories(prev =>
        prev.map(cat =>
          cat.id === selectedCategory.id ? { ...cat, name: updatedCategoryName } : cat
        )
      );
      setShowDetailsModal(false);
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteCategory = async () => {
    if (!window.confirm('Bu kategoriyi silmek istediğinize emin misiniz?')) return;

    try {
      const res = await fetch(`http://localhost:8080/rest/api/category/delete/${selectedCategory.id}`, {
        method: 'GET',
      });

      if (!res.ok) throw new Error('Kategori silinemedi');

      setCategories(prev => prev.filter(cat => cat.id !== selectedCategory.id));
      setShowDetailsModal(false);
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <Container className="mt-4">
      <h2 className="mb-3">Kategoriler</h2>

      <Row>
        {categories.map(cat => (
          <Col md={4} key={cat.id} className="mb-3">
            <Card>
              <Card.Body className="position-relative">
                <Card.Title>{cat.name}</Card.Title>
                <Button
                  size="sm"
                  variant="info"
                  className="position-absolute top-0 end-0 m-2"
                  onClick={() => openDetails(cat)}
                >
                  Detaylar
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {/* Detay Modal */}
      <Modal show={showDetailsModal} onHide={() => setShowDetailsModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Kategori Detayları</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group>
            <Form.Label>Yeni Ad</Form.Label>
            <Form.Control
              value={updatedCategoryName}
              onChange={e => setUpdatedCategoryName(e.target.value)}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={handleDeleteCategory}>
            Sil
          </Button>
          <Button variant="primary" onClick={handleUpdateCategory}>
            Güncelle
          </Button>
          <Button variant="secondary" onClick={() => setShowDetailsModal(false)}>
            Kapat
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default CategoryList;
