import '../styles/BranchListPage.css';
import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Button, Card, Modal, Form } from 'react-bootstrap';
import QrCodeGenerator from '../components/QrCodeGenerator';

function BranchListPage() {
  const [branches, setBranches] = useState([]);
  const [expandedBranchId, setExpandedBranchId] = useState(null);
  const userId = localStorage.getItem('userId');

  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const [selectedBranch, setSelectedBranch] = useState(null);
  const [showQrModal, setShowQrModal] = useState(false);

  

    const fetchBranches = async () => {
      try {
        const response = await fetch(`http://localhost:8080/rest/api/branch/user?userId=${userId}`);
        const data = await response.json();
        
        const branchList = Array.isArray(data) ? data : data.branches || [];
        setBranches(branchList);
      } catch (error) {
        console.error("Şubeler alınamadı:", error);
      }
    };

    useEffect(() => {
    fetchBranches();
  }, [userId]);


  const handleToggleBranch = (id) => {
    setExpandedBranchId(prev => prev === id ? null : id); // sadece bir bayi açılır
  };

  // Detaylar butonu için fonksiyon
  const handleShowDetails = (branch) => {
    setSelectedBranch(branch);
    setShowDetailsModal(true);
  };

  // Modalı kapatma fonksiyonu
  const handleCloseDetails = () => {
    setShowDetailsModal(false);
    setSelectedBranch(null);
  };

  // YENİ: QR kodu modalını açma/kapama fonksiyonları
  const handleShowQrModal = (branch) => {
  
    setSelectedBranch(branch);
    setShowQrModal(true);
  };
  const handleCloseQrModal = () => {
    setShowQrModal(false);
    setSelectedBranch(null);
  };

  // Şube bilgilerini güncelleme fonksiyonu (API çağrısı)
  const handleUpdateBranch = async () => {
    
   if (!selectedBranch) return;

    const branchId = selectedBranch.id;
    try {
      const response = await fetch(`http://localhost:8080/rest/api/branch/update/${branchId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(selectedBranch),
      });

      if (response.ok) {
        alert('Şube başarıyla güncellendi!');
        fetchBranches();
        handleCloseDetails();
      } else {
        alert('Güncelleme başarısız oldu.');
      }
    } catch (error) {
      console.error('Güncelleme hatası:', error);
      alert('Güncelleme sırasında bir hata oluştu.');
    }
  };

  
  
  // Şubeyi silme fonksiyonu (API çağrısı)
  const handleDeleteBranch = async () => {
    if (!selectedBranch) return;
    
    if (window.confirm(`${selectedBranch.name} adlı şubeyi silmek istediğinizden emin misiniz?`)) {
      try {
        const response = await fetch(`http://localhost:8080/rest/api/branch/delete/${selectedBranch.id}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          alert('Şube başarıyla silindi!');
          fetchBranches(); // Listeyi yenile
          handleCloseDetails();
        } else {
          alert('Silme işlemi başarısız oldu.');
        }
      } catch (error) {
        console.error('Silme hatası:', error);
        alert('Silme sırasında bir hata oluştu.');
      }
    }
  };

  return (
    <Container className="mt-4">
      <h2 className="mb-4">Şubeler</h2>
      <Row>
        {branches.map(branch => {
          const isExpanded = expandedBranchId === branch.id;

          return (
            <Col md={6} key={branch.id} className="mb-3">
              <Card className="branch-card">
                <Card.Body>
                  <div onClick={() => handleToggleBranch(branch.id)} style={{ cursor: 'pointer' }}>
                    <Card.Title>
                      {branch.name} 
                    </Card.Title>
                  </div>

                  {isExpanded && (
                    <div className="branch-details mt-3">         
                      <Card.Text><strong>Şube Adı:</strong> {branch.name}</Card.Text>
                      <Card.Text><strong>Adres:</strong> {branch.address}</Card.Text>
                      <Card.Text><strong>Telefon:</strong> {branch.phoneNumber}</Card.Text>
                      <Card.Text><strong>Restoran Kodu:</strong> {branch.restaurantCode}</Card.Text>

                      <div className="d-flex justify-content-between align-items-center mt-3">
                      {/* Sol tarafta Detaylar butonu */}
                      <Button onClick={() => handleShowDetails(branch)} variant="primary">
                        Detaylar
                      </Button>
                      
                      {/* Sağ tarafta QR Kodu butonu */}
                      <Button onClick={() => handleShowQrModal(branch)} variant="info" className="ms-2">
                        QR Kodu
                      </Button>
                    </div>
                    </div> 
                  )}
                </Card.Body>
              </Card>
            </Col>
          );
        })}
      </Row>

      {/* Detaylar Modal'ı */}
      <Modal show={showDetailsModal} onHide={handleCloseDetails}>
        <Modal.Header closeButton>
          <Modal.Title>{selectedBranch?.name} Detayları</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {selectedBranch && (
            <Form>
              <Form.Group className="mb-3">
                <Form.Label>Şube Adı</Form.Label>
                <Form.Control
                  type="text"
                  value={selectedBranch.name}
                  onChange={e => setSelectedBranch({ ...selectedBranch, name: e.target.value })}
                />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Adres</Form.Label>
                <Form.Control
                  type="text"
                  value={selectedBranch.address}
                  onChange={e => setSelectedBranch({ ...selectedBranch, address: e.target.value })}
                />
              </Form.Group>
              <Form.Group className="mb-3">
                <Form.Label>Telefon Numarası</Form.Label>
                <Form.Control
                  type="text"
                  value={selectedBranch.phoneNumber}
                  onChange={e => setSelectedBranch({ ...selectedBranch, phoneNumber: e.target.value })}
                />
              </Form.Group>
            </Form>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="danger" onClick={handleDeleteBranch}>
            Sil
          </Button>
          <Button variant="success" onClick={handleUpdateBranch}>
            Güncelle
          </Button>
          <Button variant="secondary" onClick={handleCloseDetails}>
            Kapat
          </Button>
        </Modal.Footer>
      </Modal>

      {/* YENİ: QR Kodu Modal'ı */}
      <Modal show={showQrModal} onHide={handleCloseQrModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>{selectedBranch?.name} QR Kodu</Modal.Title>
        </Modal.Header>
        <Modal.Body className="text-center">
          {selectedBranch && (
            <QrCodeGenerator restaurantCode={selectedBranch.restaurantCode} />
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseQrModal}>
            Kapat
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
}

export default BranchListPage;
