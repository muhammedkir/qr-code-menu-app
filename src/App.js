import { BrowserRouter , Routes, Route } from 'react-router-dom';
import { Card } from 'react-bootstrap';
import Login from './pages/Login';
import Register from './pages/Register';
import CreateBranch from './pages/CreateBranch';
import LandingPage from './pages/LandingPage';
import BranchListPage from './pages/BranchListPage';
import Settings from './pages/Setting';
import Menu from './pages/Menu';
import HomeLayout from './pages/HomeLayout';
import 'bootstrap/dist/css/bootstrap.min.css';
import CategoryList from './pages/CategoryList';
import AddProductForm from './pages/AddProductForm';
import CategoryCreateForm from './pages/CategoryCreateForm';

          


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />


         <Route path="/home" element={<HomeLayout />}>

          {/* Hoşgeldin kartı (Ana sayfa) */}
          <Route index element={
            <Card className="welcome-card">
              <Card.Body>
                <h2>QR Kod Menü Yönetim Paneline Hoş geldiniz!</h2>
                <p>Kullanıcı: <strong>{localStorage.getItem('username') || 'Kullanıcı'}</strong></p>
                <p>Soldaki menüyü kullanarak işlem yapabilirsiniz.</p>
              </Card.Body>
            </Card>
          } />

          <Route path="create-branch" element={<CreateBranch />} />
          <Route path="branch-list" element={<BranchListPage />} />
          <Route path="category-create" element={<CategoryCreateForm />} />
          <Route path="category-list" element={<CategoryList />} />
          <Route path="menu-list" element={<Menu />} />
          <Route path="product-add" element={<AddProductForm />} />
          <Route path="settings" element={<Settings />} />
          
        </Route>
  
      </Routes>
    </BrowserRouter>
  );
}

export default App;
