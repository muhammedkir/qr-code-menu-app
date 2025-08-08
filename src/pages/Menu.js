import React, { useEffect, useState, useRef } from 'react';
import { Container, Row, Col, Card, Nav, Spinner, Alert } from 'react-bootstrap';
import '../styles/Menu.css';

function MenuPage() {
  const [menu, setMenu] = useState([]);
  const [branchInfo, setBranchInfo] = useState(null); // Bayi bilgileri için state
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const selectedBranchId = localStorage.getItem("branchId");
  const categoryRefs = useRef({});



  useEffect(() => {
    const fetchMenuData = async () => {
      if (!selectedBranchId) {
        setError("Şube bilgisi bulunamadı. Lütfen giriş yapın.");
        setLoading(false);
        return;
      }
      setLoading(true);
      setError(null);
      try {
        // Bayi bilgilerini çek
        const branchResponse = await fetch(`http://localhost:8080/rest/api/branch/list/${selectedBranchId}`);
        if (branchResponse.ok) {
          const branchData = await branchResponse.json();
          console.log('Branch data:', branchData); // Debug için
          setBranchInfo(branchData);
        } else {
          console.error('Branch fetch failed:', branchResponse.status);
        }

        // Kategorileri çek
        const categoriesResponse = await fetch(`http://localhost:8080/rest/api/category/branch?branchId=${selectedBranchId}`);
        if (!categoriesResponse.ok) throw new Error('Kategori yüklenirken bir hata oluştu.');
        const categoriesData = await categoriesResponse.json();
        if (categoriesData.length === 0) {
          setError("Menüde kategori bulunmamaktadır.");
          setLoading(false);
          return;
        }
        const menuWithProducts = await Promise.all(categoriesData.map(async category => {
          try {
            const productsResponse = await fetch(`http://localhost:8080/rest/api/product/by-category?categoryId=${category.id}`);
            if (!productsResponse.ok) return { ...category, products: [] };
            const productsData = await productsResponse.json();
            return { ...category, products: productsData };
          } catch (err) {
            console.error(`'${category.name}' kategorisi için ürünler çekilirken hata oluştu:`, err);
            return { ...category, products: [] };
          }
        }));
        setMenu(menuWithProducts);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchMenuData();
  }, [selectedBranchId]);

  const handleCategoryScroll = (categoryId) => {
    if (categoryRefs.current[categoryId]) {
      categoryRefs.current[categoryId].scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  };

  return (
    <Container className="menu-page">
      {loading && <div className="d-flex justify-content-center align-items-center" style={{ height: '80vh' }}><Spinner animation="border" /></div>}
      {error && <Alert variant="danger" className="text-center">{error}</Alert>}
      
      {!loading && !error && (
        <>
          {/* Bayi Header */}
          {branchInfo && (
            <div className="branch-header text-center mb-4 py-4" style={{ backgroundColor: '#f8f9fa', borderRadius: '8px' }}>
              <div className="d-flex align-items-center justify-content-center">
                {branchInfo.logoPath && (
                  <img
                    src={`http://localhost:8080/uploads/${branchInfo.logoPath}`}
                    alt={`${branchInfo.name} Logo`}
                    style={{ 
                      width: '80px', 
                      height: '80px', 
                      objectFit: 'contain', 
                      marginRight: '20px',
                      borderRadius: '8px'
                    }}
                    onError={(e) => {
                      console.error('Logo yüklenemedi:', e.target.src);
                      e.target.style.display = 'none';
                    }}
                  />
                )}
                <h1 className="branch-name mb-0" style={{ fontSize: '2.5rem', fontWeight: 'bold', color: '#333' }}>
                  {branchInfo.name}
                </h1>
              </div>
              {branchInfo.address && (
                <p className="branch-address text-muted mt-2 mb-0">
                  📍 {branchInfo.address}
                </p>
              )}
            </div>
          )}
        
          {/* Kategori Navigasyonu (Butonlar) */}
          <Nav className="category-nav mb-4 d-flex flex-nowrap overflow-auto" style={{ position: 'sticky', zIndex: 1020, padding: '1rem 0', }}>
            {menu.map(categoryItem => (
              <Nav.Item key={categoryItem.id} className="category-nav-item" onClick={() => handleCategoryScroll(categoryItem.id)}>
                <Nav.Link className="category-link">
                  <div className="category-image-container ">
                    <img
                      src={categoryItem.imagePath ? `http://localhost:8080/uploads/${categoryItem.imagePath}` : "https://via.placeholder.com/120"}
                      alt={categoryItem.name}
                      className="category-image"
                    />
                  </div>
                  <span className="category-name">{categoryItem.name}</span>
                </Nav.Link>
              </Nav.Item>
            ))}
          </Nav>

          {/* Tüm Kategoriler ve Ürünleri Alt Alta */}
          {menu.map(categoryItem => (
            <div key={categoryItem.id} ref={el => categoryRefs.current[categoryItem.id] = el}>
              <h2 className="category-title mt-5 mb-4">{categoryItem.name}</h2>
              {categoryItem.products.length > 0 ? (
                <div className="product-list-container">
                  <Row xs={1} md={2} lg={3} className="g-4 product-grid">
                    {categoryItem.products.map(product => (
                      <Col key={product.id}>
                        <Card className="product-card h-100 product-card-animation">
                          <Row noGutters>
                            <Col xs={4}>
                              <div className="product-image-container">
                                <img
                                  src={`http://localhost:8080/uploads/${product.imagePath}` || "https://via.placeholder.com/250"}
                                  alt={product.name}
                                  className="product-image-small"
                                />
                              </div>
                            </Col>
                            <Col xs={8}>
                              <Card.Body className="d-flex flex-column">
                                <div className="d-flex justify-content-between align-items-start mb-2">
                                  <Card.Title className="product-title m-0">{product.name}</Card.Title>
                                  <span className="product-price fw-bold">{product.price} ₺</span>
                                </div>
                                <div className="divider mb-2"></div>
                                <Card.Text className="product-description flex-grow-1">
                                  {product.description}
                                </Card.Text>
                                {product.specialNote && (
                                  <span className="product-note badge rounded-pill bg-warning text-dark">
                                    {product.specialNote}
                                  </span>
                                )}
                              </Card.Body>
                            </Col>
                          </Row>
                        </Card>
                      </Col>
                    ))}
                  </Row>
                </div>
          ) : (
            <Alert variant="info" className="text-center mt-3">Bu kategoride ürün bulunmamaktadır.</Alert>
          )}
            </div>
          ))}
              </>
            )}
          </Container>
        );
}

export default MenuPage;