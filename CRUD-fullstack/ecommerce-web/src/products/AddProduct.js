import React, { useState, useEffect } from 'react'
import axios from "axios";
import { useNavigate } from 'react-router-dom';

export default function AddProduct() {

    const [brands, setBrands] = useState([])
    const [categories, setCategories] = useState([])
    const [colors, setColors] = useState([])
    const [sizes, setSizes] = useState([])

    useEffect(() => {
        loadBrands();
        loadCategories();
        loadColors();
        loadSizes();
    }, []);

    const loadBrands = async () => {
        const result = await axios.get("http://localhost:8080/brands")
        setBrands(result.data)
    }

    const loadCategories = async () => {
        const result = await axios.get("http://localhost:8080/categories")
        setCategories(result.data)
    }

    const loadColors = async () => {
        const result = await axios.get("http://localhost:8080/colors")
        setColors(result.data)
    }

    const loadSizes = async () => {
        const result = await axios.get("http://localhost:8080/sizes")
        setSizes(result.data)
    }
    // ----------------------------------------------------------

    let navigate = useNavigate();

    const [product, setProduct] = useState({
        name: "",
        description: "",
        price: "",
        brand:"",
        category:"",
        size:"",
        color:""
    });

    const { name, description, price, brand, category, size, color } = product;

    const onInputChange = (e) => {
        setProduct({ ...product, [e.target.name]: e.target.value })
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        await axios.post("http://localhost:8080/product", product)
        navigate("/");
    }

    return (
        <div classNameName='container'>
            <br></br>
            <br></br>

            <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                <br></br>
                <h1>Crear producto</h1>
                <br></br>

                <form onSubmit={(e) => onSubmit(e)}>
                    <div className="mb-3">
                        <label for="exampleInputEmail1" className="form-label">Name</label>
                        <input type={"text"} name='name' value={name} onChange={(e) => onInputChange(e)} className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"></input>
                    </div>
                    <div className="mb-3">
                        <label for="exampleInputPassword1" className="form-label">Description</label>
                        <input type={"text"} name='description' value={description} onChange={(e) => onInputChange(e)} className="form-control" id="exampleInputPassword1"></input>
                    </div>
                    <div className="mb-3">
                        <label for="exampleInputPassword1" className="form-label">Price</label>
                        <input type={"number"} name='price' value={price} onChange={(e) => onInputChange(e)} className="form-control" id="exampleInputPassword1"></input>
                    </div>


                    <select class="form-select" name="brand" aria-label="Default select example">
                        <option selected>Marcas</option>
                        {
                            brands.map((brand, index) => (
                                <option key={index} value={brand}>{brand.brand}</option>
                            ))
                        }
                    </select>
                    <br></br>
                    <select class="form-select" name="categorie" aria-label="Default select example">
                        <option selected>Categorias</option>
                        {
                            categories.map((category, index) => (
                                <option key={index} value={category.id}>{category.category}</option>
                            ))
                        }
                    </select>

                    <br></br>

                    <select class="form-select" name="size" aria-label="Default select example">
                        <option selected>Talla</option>
                        {
                            sizes.map((size, index) => (
                                <option key={index} value={size.id}>{size.size}</option>
                            ))
                        }
                    </select>

                    <br></br>

                    <select class="form-select" name="color" aria-label="Default select example">
                        <option selected>Colores</option>
                        {
                            colors.map((color, index) => (
                                <option key={index} value={color.id}>{color.color}</option>
                            ))
                        }
                    </select>

                    <br></br>


                    <button type="submit" className="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    )
}
