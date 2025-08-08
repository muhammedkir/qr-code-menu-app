import React, { useState } from 'react';

function LogoUploader({ branchId }) {
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewBase64, setPreviewBase64] = useState('');

  const handleFileChange = async (e) => {
    const file = e.target.files[0];
    setSelectedFile(file);

    const reader = new FileReader();
    reader.onloadend = () => {
      setPreviewBase64(reader.result.split(',')[1]); // sadece base64 kısmını al
    };
    reader.readAsDataURL(file);
  };

  const handleUpload = async () => {
    if (!previewBase64 || !branchId) return;

    const response = await fetch(`http://localhost:8080/rest/api/branch/upload-logo`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        branchId: branchId,
        logoBase64: previewBase64,
      }),
    });

    if (response.ok) {
      alert('Logo başarıyla yüklendi');
    } else {
      alert('Logo yüklenemedi');
    }
  };

  return (
    <div>
      <input type="file" onChange={handleFileChange} accept="image/*" />
      {previewBase64 && (
        <img
          src={`data:image/png;base64,${previewBase64}`}
          alt="Preview"
          style={{ width: '150px', marginTop: '10px' }}
        />
      )}
      <br />
      <button onClick={handleUpload}>Logoyu Yükle</button>
    </div>
  );
}

export default LogoUploader;
