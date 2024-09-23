import './App.css';
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Navbar from './layout/Navbar';
import Home from './pages/Home';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import AddProduct from './products/AddProduct';
import EditProduct from './products/EditProduct';

function App() {
  return (
    <div className="App">
     <Router>

     <Navbar />

      <Routes>
        <Route exact path="/" element={<Home/>} />
        <Route exact path="/add-product" element={<AddProduct/>} />
        <Route exact path="/edit-product/:id" element={<EditProduct/>} />
      </Routes>
     
     </Router>
    </div>
  );
}

export default App;
