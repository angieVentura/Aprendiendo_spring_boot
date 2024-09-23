import React, { useEffect, useState } from 'react'
import axios from "axios";
import { Link } from 'react-router-dom';

export default function Home() {

    const [products, setProducts] = useState([])

    useEffect(() => {
        loadProduct();
    }, []);

    const loadProduct = async () => {
        const result = await axios.get("http://localhost:8080/products")
        setProducts(result.data)
    }

    const deleteProduct = async (id) => {
        const result = await axios.delete(`http://localhost:8080/product/${id}`);
        loadProduct();
    }

    return (
        <div className='container'>

            <br></br>
            <br></br>
            <h1>Lista de productos</h1>
            <br></br>

            <div className='py-4'>
                <table className="table  shadow">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Price</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            products.map((product, index) => (
                                <tr>
                                    <th scope="row" key={index}>{index + 1}</th>
                                    <td>{product.name}</td>
                                    <td>{product.description}</td>
                                    <td>{product.price}</td>
                                    <td>
                                        {/* <button className='btn btn-outline-info mx-2'>View</button> */}
                                        <Link to={`/edit-product/${product.id}`} className='btn btn-outline-warning mx-2'>Edit</Link>
                                        <button onClick={() => deleteProduct(product.id)} className='btn btn-outline-danger mx-2'>Delete</button>
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}
