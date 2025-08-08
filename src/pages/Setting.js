import React, { useEffect, useState } from 'react';
import { Form, Button, Container, Row, Col, Alert, Spinner,Card, Modal } from 'react-bootstrap';
import '../styles/Settings.css';

function Settings() {
  const [userData, setUserData] = useState(null);
  const [branches, setBranches] = useState([]);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [selectedBranch, setSelectedBranch] = useState(null); // Düzenlenecek şube için yeni state
  const [showBranchModal, setShowBranchModal] = useState(false); // Modal görünürlüğü için yeni state
  const [expandedBranchId, setExpandedBranchId] = useState(null);
  const userId = localStorage.getItem('userId');

  // Verileri getir
  useEffect(() => {
    async function fetchData() {
      try {
        const userRes = await fetch(`http://localhost:8080/rest/api/user/info/${userId}`);
        const userJson = await userRes.json();
        setUserData(userJson);

        const branchRes = await fetch(`http://localhost:8080/rest/api/branch/user?userId=${userId}`);
        const branchData = await branchRes.json();

        if(Array.isArray(branchData)) {
          setBranches(branchData); // İlk şubeyi al
        }
      } catch (err) {
        console.error('Veriler alınamadı:', err);
      } finally{
        setLoading(false);
      }
    }

    fetchData();
  }, [userId]);

  // Şube adını güncellemek için bir fonksiyon
  const handleBranchChange = (e, index, field) => {
    const newBranches = [...branches]; // Mevcut dizinin kopyasını oluştur
    newBranches[index] = { ...newBranches[index],[field]:  e.target.value }; // İlgili şubeyi güncelle
    setBranches(newBranches); // Yeni diziyi state'e ata
  };

  const handleSave = async (e) => {
    e.preventDefault();

    try {
      const userUpdate = await fetch(`http://localhost:8080/rest/api/user/update/${userId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData),
      });

      // Her bir şube için güncelleme çağrılarını hazırla
      const branchUpdatePromises = branches.map(branch => {
        console.log("Güncellenecek şube verisi:", branch);
        // Her şubenin id'si olduğundan emin ol
        if (branch.id) {
          return fetch(`http://localhost:8080/rest/api/branch/update/${branch.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(branch),
          });
        }
        return Promise.resolve({ ok: true }); // ID'si olmayan şube için başarılı kabul et
      });

      // Tüm güncelleme çağrılarını paralel olarak çalıştır
      const branchUpdateResults = await Promise.all(branchUpdatePromises);
      const branchUpdateOk = branchUpdateResults.every(res => res.ok);

      if (userUpdate.ok && branchUpdateOk) {
        setMessage('Bilgiler başarıyla güncellendi.');
      } else {
        setMessage('Güncelleme sırasında hata oluştu.');
      }
    } catch (err) {
      setMessage('Sunucu hatası.');
      console.error(err);
    }
  };

  // Açılır-kapanır mantığı için fonksiyon
  const handleToggleBranch = (id) => {
    setExpandedBranchId(prev => (prev === id ? null : id));
  };

   // Modal açma
    const handleShowBranchModal = (branch) => {
        setSelectedBranch(branch);
        setShowBranchModal(true);
    };

    // Modal kapatma
    const handleCloseBranchModal = () => {
        setShowBranchModal(false);
        setSelectedBranch(null);
    };

 return (
    <Container className="settings-container">
      <Row className="justify-content-center">
        <Col md={8}>
          
          {loading ? <Spinner animation="border" variant="primary" /> : (
            <Form onSubmit={handleSave}>
              <h5>Kullanıcı Bilgileri</h5>
              <Form.Group controlId="formUsername" className="mb-3">
                <Form.Label>Kullanıcı Adı</Form.Label>
                <Form.Control
                  type="text"
                  value={userData?.username || ''}
                  onChange={(e) => setUserData({ ...userData, username: e.target.value })}
                />
              </Form.Group>

              <Form.Group controlId="formEmail" className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  value={userData?.email || ''}
                  onChange={(e) => setUserData({ ...userData, email: e.target.value })}
                />
              </Form.Group>

              <h5>Şube Bilgileri</h5>
              {branches.length > 0 ? (
                branches.map((branch, index) => {
                  const isExpanded = expandedBranchId === branch.id;
                  return (
                    <Card key={branch.id || index} className="mb-3">
                      <Card.Body>
                        <div className="d-flex justify-content-between align-items-center">
                          <Card.Title className="mb-0">{branch.name}</Card.Title>
                          <Button
                            variant={isExpanded ? 'secondary' : 'outline-secondary'}
                            size="sm"
                            onClick={() => handleToggleBranch(branch.id)}
                          >
                            {isExpanded ? 'Gizle' : 'Detaylar'}
                          </Button>
                        </div>

                        {isExpanded && (
                          <div className="mt-3">
                            <Form.Group controlId={`formBranchName-${index}`} className="mb-2">
                              <Form.Label>Şube Adı</Form.Label>
                              <Form.Control
                                type="text"
                                value={branch.name || ''}
                                onChange={(e) => handleBranchChange(e, index, 'name')}
                              />
                            </Form.Group>
                            <Form.Group controlId={`formBranchAddress-${index}`} className="mb-2">
                              <Form.Label>Adres</Form.Label>
                              <Form.Control
                                type="text"
                                value={branch.address || ''}
                                onChange={(e) => handleBranchChange(e, index, 'address')}
                              />
                            </Form.Group>
                            <Form.Group controlId={`formBranchPhone-${index}`} className="mb-2">
                              <Form.Label>Telefon Numarası</Form.Label>
                              <Form.Control
                                type="text"
                                value={branch.phoneNumber || ''}
                                onChange={(e) => handleBranchChange(e, index, 'phoneNumber')}
                              />
                            </Form.Group>
                          </div>
                        )}
                      </Card.Body>
                    </Card>
                  );
                })
              ) : (
                <p>Kayıtlı şube bulunmamaktadır.</p>
              )}

              <Button type="submit" variant="success">Kaydet</Button>
            </Form>
          )}
          {message && <Alert variant="info" className="mt-3">{message}</Alert>}
        </Col>
      </Row>
    </Container>
  );
}

export default Settings;
