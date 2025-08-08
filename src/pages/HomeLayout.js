import React,{useEffect, useState} from 'react';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import { Container, Row, Col, Button,Card,Form } from 'react-bootstrap';
import '../styles/HomeLayout.css'; 

function HomeLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const [branches, setBranches] = useState([]);
  const [selectedBranchId, setSelectedBranchId] = useState(localStorage.getItem('branchId') || '');
  const [sidebarOpen, setSidebarOpen] = useState(true);

  
   useEffect(() => {

    const isMobile = window.innerWidth < 768;
    setSidebarOpen(!isMobile); // mobilde kapalı, desktopta açık

    const userId = localStorage.getItem('userId');
    if (!userId) return;

    fetch(`http://localhost:8080/rest/api/branch/user?userId=${userId}`)
      .then(res => res.json())
      .then(data => {
        setBranches(data);
        // Eğer seçili şube yoksa ilk şubeyi seç
        if (!selectedBranchId && data.length > 0) {
          setSelectedBranchId(data[0].id);

          const selectedBranch = data.find(branch => branch.id === Number(selectedBranchId));
        if (selectedBranch && selectedBranch.logoPath) {
            localStorage.setItem('branchLogoPath', selectedBranch.logoPath);
        }
          localStorage.setItem('branchId', data[0].id);
        }
      })
      .catch(err => console.error('Şubeler alınamadı:', err));
  }, [selectedBranchId]);

  const isActive = (path) => location.pathname === path;


  const handleBranchChange = (e) => {
    const newId = e.target.value;
    setSelectedBranchId(newId);
    localStorage.setItem('branchId', newId);

    const newSelectedBranch = branches.find(branch => branch.id === newId);
    if (newSelectedBranch && newSelectedBranch.logoPath) {
        localStorage.setItem('branchLogoPath', newSelectedBranch.logoPath);
    } else {
        localStorage.removeItem('branchLogoPath');
    }
    };

  return (
    <Container fluid className="homepage-container d-flex flex-column min-vh-100">

    {/* Menü Toggle Butonu */}
      {!sidebarOpen && (
          <div className="hamburger-wrapper">
            <Button
              variant="light"
              className="hamburger-btn d-none d-md-block"
              onClick={() => setSidebarOpen(true)}
            >
              ☰
            </Button>
          </div>
        )}

      <Row className="flex-grow-1 flex-wrap">
        {/* Sidebar */}
        {sidebarOpen && (
        <Col xs={12} md={3} className="sidebar d-flex flex-column sidebar-transition">
         <div className="sidebar-header d-flex justify-content-between align-items-center mb-3">
           <h4 className="sidebar-title">QR Menü Paneli</h4>
           <button className="close-btn" onClick={() => setSidebarOpen(false)}>✖️</button>
         </div>
          {/* 🔽 Şube Seç Dropdown */}
          <Form.Group controlId="branchSelect" className="mb-3">
            <Form.Label>Şube Seç</Form.Label>
            <Form.Select value={selectedBranchId} onChange={handleBranchChange}>
              {branches.map((branch) => (
                <option key={branch.id} value={branch.id}>
                  {branch.name}
                </option>
              ))}
            </Form.Select>
          </Form.Group>


          <ul className="sidebar-list flex-grow-1">
      <li
        className={isActive('/home') ? 'active' : ''}
        onClick={() => navigate('/home')}
      >
        🏠 Anasayfa
      </li>
      <li
        className={isActive('/home/create-branch') ? 'active' : ''}
        onClick={() => navigate('/home/create-branch')}
      >
        🏢 Şube Oluştur
      </li>
      <li
        className={isActive('/home/branch-list') ? 'active' : ''}
        onClick={() => navigate('/home/branch-list')}
      >
        📋 Şubeleri Listele
      </li>
      <li
        className={isActive('/home/category-create') ? 'active' : ''}
        onClick={() => navigate('/home/category-create')}
        >
        🗂️ Kategori Oluştur
        </li>
      <li 
        className={isActive('/home/category-list') ? 'active' : ''}
        onClick={() => navigate('/home/category-list')}>
        🏷️ Kategorileri Listele
      </li>
      <li
        className={isActive('/home/menu-list') ? 'active' : ''}
        onClick={() => navigate('/home/menu-list')}>
        🍽️ Menü 
        </li>
        <li 
        className={isActive('/home/product-add') ? 'active' : ''}
        onClick={() => navigate('/home/product-add')}>
        🛒 Ürün Ekle
      </li>
      <li
        className={isActive('/home/settings') ? 'active' : ''}
        onClick={() => navigate('/home/settings')}
      >
        ⚙️ Ayarlar
      </li>
    </ul> 
          <div className="logout-container mt-auto">
            <Button variant="danger" onClick={() => navigate('/')} className="logout-btn">
              🔒 Çıkış Yap
            </Button>
          </div>
        </Col>
        )}

        {/* Main content */}
        <Col xs={12} md={sidebarOpen ? 9 : 12} className="content-area flex-grow-1">
          <Card className="welcome-card p-3">
            <Card.Body>
              <Outlet />
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Footer */}
      <footer className="text-center py-3 bg-light mt-auto border-top">
        <small>© {new Date().getFullYear()} QR Menü Yönetim Sistemi</small>
      </footer>
    </Container>
  );
}

export default HomeLayout;
