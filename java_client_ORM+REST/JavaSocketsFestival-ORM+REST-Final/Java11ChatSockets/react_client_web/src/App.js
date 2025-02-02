// // import React, { useState, useEffect } from 'react';
// // import axios from 'axios';
// //
// // const App = () => {
// //   const [spectacole, setSpectacole] = useState([]);
// //   const [selectedSpectacol, setSelectedSpectacol] = useState(null);
// //   const [form, setForm] = useState({ id: '', name: '', otherFields: '' }); // Adaugă câmpurile necesare
// //
// //   useEffect(() => {
// //     fetchSpectacole();
// //   }, []);
// //
// //   const fetchSpectacole = async () => {
// //     try {
// //       const response = await axios.get('/festival/spectacol');
// //       setSpectacole(response.data);
// //     } catch (error) {
// //       console.error('Error fetching spectacole:', error);
// //     }
// //   };
// //
// //   const handleSelectSpectacol = (spectacol) => {
// //     setSelectedSpectacol(spectacol);
// //     setForm({ ...spectacol });
// //   };
// //
// //   const handleChange = (e) => {
// //     const { name, value } = e.target;
// //     setForm({ ...form, [name]: value });
// //   };
// //
// //   const handleSubmit = async (e) => {
// //     e.preventDefault();
// //     try {
// //       if (form.id) {
// //         await axios.put(`/festival/spectacol/${form.id}`, form);
// //       } else {
// //         await axios.post('/festival/spectacol', form);
// //       }
// //       fetchSpectacole();
// //     } catch (error) {
// //       console.error('Error submitting form:', error);
// //     }
// //   };
// //
// //   const handleDelete = async (id) => {
// //     try {
// //       await axios.delete(`/festival/spectacol/${id}`);
// //       fetchSpectacole();
// //     } catch (error) {
// //       console.error('Error deleting spectacol:', error);
// //     }
// //   };
// //
// //   return (
// //       <div>
// //         <h1>Festival Spectacole</h1>
// //         <table>
// //           <thead>
// //           <tr>
// //             <th>ID</th>
// //             <th>Name</th>
// //             <th>Actions</th>
// //           </tr>
// //           </thead>
// //           <tbody>
// //           {spectacole.map((spectacol) => (
// //               <tr key={spectacol.id}>
// //                 <td>{spectacol.id}</td>
// //                 <td>{spectacol.name}</td>
// //                 <td>
// //                   <button onClick={() => handleSelectSpectacol(spectacol)}>Select</button>
// //                   <button onClick={() => handleDelete(spectacol.id)}>Delete</button>
// //                 </td>
// //               </tr>
// //           ))}
// //           </tbody>
// //         </table>
// //
// //         <h2>{form.id ? 'Update Spectacol' : 'Create Spectacol'}</h2>
// //         <form onSubmit={handleSubmit}>
// //           <div>
// //             <label>ID: </label>
// //             <input type="text" name="id" value={form.id} onChange={handleChange} disabled />
// //           </div>
// //           <div>
// //             <label>Name: </label>
// //             <input type="text" name="name" value={form.name} onChange={handleChange} />
// //           </div>
// //           {/* Adaugă câmpurile necesare */}
// //           <button type="submit">Submit</button>
// //         </form>
// //       </div>
// //   );
// // };
// //
// // export default App;
// //
// //
// //
// //
// //
// //
// // //  "scripts": {
// // //    "start": "react-scripts start",
// // //    "build": "react-scripts build",
// // //    "test": "react-scripts test",
// // //    "eject": "react-scripts eject"
// // //  },
//
// import React, { useState, useEffect } from 'react';
// import axios from 'axios';
//
// const App = () => {
//   const [spectacole, setSpectacole] = useState([]);
//   const [selectedSpectacol, setSelectedSpectacol] = useState(null);
//   const [form, setForm] = useState({ id_artist: '', locatie: '', data_inc: '', nr_locuri: '', id: '' });
//
//   useEffect(() => {
//     fetchSpectacole();
//   }, []);
//
//   const fetchSpectacole = async () => {
//     try {
//       const response = await axios.get('/festival/spectacol');
//       setSpectacole(response.data);
//     } catch (error) {
//       console.error('Error fetching spectacole:', error);
//     }
//   };
//
//   const handleSelectSpectacol = (spectacol) => {
//     setSelectedSpectacol(spectacol);
//     setForm({ ...spectacol });
//   };
//
//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setForm({ ...form, [name]: value });
//   };
//
//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       if (form.id) {
//         await axios.put(`/festival/spectacol/${form.id}`, form);
//       } else {
//         await axios.post('/festival/spectacol', form);
//       }
//       fetchSpectacole();
//       setForm({ id_artist: '', locatie: '', data_inc: '', nr_locuri: '', id: '' });
//     } catch (error) {
//       console.error('Error submitting form:', error);
//     }
//   };
//
//   const handleDelete = async (id) => {
//     try {
//       await axios.delete(`/festival/spectacol/${id}`);
//       fetchSpectacole();
//     } catch (error) {
//       console.error('Error deleting spectacol:', error);
//     }
//   };
//
//   return (
//       <div>
//         <h1>Festival Spectacole</h1>
//         <table>
//           <thead>
//           <tr>
//             <th>ID Artist</th>
//             <th>Locatie</th>
//             <th>Data Inc</th>
//             <th>Nr Locuri</th>
//             <th>ID</th>
//             {/*<th>Actions</th>*/}
//           </tr>
//           </thead>
//           <tbody>
//           {spectacole.map((spectacol) => (
//               <tr key={spectacol.id}>
//                 <td>{spectacol.id_artist}</td>
//                 <td>{spectacol.locatie}</td>
//                 <td>{spectacol.data_inc}</td>
//                 <td>{spectacol.nr_locuri}</td>
//                 <td>{spectacol.id}</td>
//                 <td>
//                   <button onClick={() => handleSelectSpectacol(spectacol)}>Select</button>
//                   <button onClick={() => handleDelete(spectacol.id)}>Delete</button>
//                 </td>
//               </tr>
//           ))}
//           </tbody>
//         </table>
//
//         <h2>{form.id ? 'Update Spectacol' : 'Create Spectacol'}</h2>
//         <form onSubmit={handleSubmit}>
//           <div>
//             <label>ID Artist: </label>
//             <input type="text" name="id_artist" value={form.id_artist} onChange={handleChange} />
//           </div>
//           <div>
//             <label>Locatie: </label>
//             <input type="text" name="locatie" value={form.locatie} onChange={handleChange} />
//           </div>
//           <div>
//             <label>Data Inc: </label>
//             <input type="datetime-local" name="data_inc" value={form.data_inc} onChange={handleChange} />
//           </div>
//           <div>
//             <label>Nr Locuri: </label>
//             <input type="number" name="nr_locuri" value={form.nr_locuri} onChange={handleChange} />
//           </div>
//           <div>
//             <label>ID: </label>
//             <input type="text" name="id" value={form.id} onChange={handleChange} disabled />
//           </div>
//           <button type="submit">Submit</button>
//         </form>
//       </div>
//   );
// };
//
// export default App;

import React, { useState, useEffect } from 'react';

const App = () => {
  const [spectacole, setSpectacole] = useState([]);
  const [selectedSpectacol, setSelectedSpectacol] = useState(null);
  const [form, setForm] = useState({ id_artist: '', locatie: '', data_inc: '', nr_locuri: '', id: '' });

  useEffect(() => {
    console.log("Fetching spectacole...");
    fetchSpectacole();
  }, []);

  const fetchSpectacole = async () => {
    try {
      const response = await fetch('http://localhost:8080/festival/spectacol');
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      console.log("Data received from server:", data); // Verifică datele primite de la server

      setSpectacole(data);
    } catch (error) {
      console.error('Error fetching spectacole:', error);
    }
  };

  const handleSelectSpectacol = (spectacol) => {
    setSelectedSpectacol(spectacol);
    setForm({ ...spectacol });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (form.id) {
        await fetch(`http://localhost:8080/festival/spectacol/${form.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(form),
        });
      } else {
        await fetch('http://localhost:8080/festival/spectacol', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(form),
        });
      }
      fetchSpectacole();
      setForm({ id_artist: '', locatie: '', data_inc: '', nr_locuri: '', id: '' });
    } catch (error) {
      console.error('Error submitting form:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await fetch(`http://localhost:8080/festival/spectacol/${id}`, {
        method: 'DELETE',
      });
      fetchSpectacole();
    } catch (error) {
      console.error('Error deleting spectacol:', error);
    }
  };
  const hasSpectacole = spectacole.length > 0;
  console.log(hasSpectacole);

  return (
      <div>
        <h1>Festival Spectacole</h1>
        <table>
          <thead>
          <tr>
            <th>ID Artist</th>
            <th>Locatie</th>
            <th>Data Inc</th>
            <th>Nr Locuri</th>
            <th>ID</th>
            <th>Action</th>

          </tr>
          </thead>
          <tbody>
          {spectacole.map((spectacol) => (
              <tr key={spectacol.id}>
                <td>{spectacol.id_artist}</td>
                <td>{spectacol.locatie}</td>
                <td>{spectacol.data_inc}</td>
                <td>{spectacol.nr_locuri}</td>
                <td>{spectacol.id}</td>
                <td>
                  <button onClick={() => handleSelectSpectacol(spectacol)}>Select</button>
                  <button onClick={() => handleDelete(spectacol.id)}>Delete</button>
                </td>
              </tr>
          ))}
          </tbody>
        </table>

        <h2>{form.id ? 'Update Spectacol' : 'Create Spectacol'}</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label>ID Artist: </label>
            <input type="text" name="id_artist" value={form.id_artist} onChange={handleChange} />
          </div>
          <div>
            <label>Locatie: </label>
            <input type="text" name="locatie" value={form.locatie} onChange={handleChange} />
          </div>
          <div>
            <label>Data Inc: </label>
            <input type="datetime-local" name="data_inc" value={form.data_inc} onChange={handleChange} />
          </div>
          <div>
            <label>Nr Locuri: </label>
            <input type="number" name="nr_locuri" value={form.nr_locuri} onChange={handleChange} />
          </div>
          <div>
            <label>ID: </label>
            <input type="text" name="id" value={form.id} onChange={handleChange} disabled />
          </div>
          <button type="submit">Submit</button>
        </form>
      </div>
  );
};

export default App;
