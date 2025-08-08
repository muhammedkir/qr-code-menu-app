import React from 'react';
import { QRCodeSVG } from 'qrcode.react';

// Bu bileşen, prop olarak alacağı URL'den QR kodu oluşturur
  const QrCodeGenerator = ({ restaurantCode }) => {
     const menuUrl = `http://localhost:3000/menu/${restaurantCode}`;

  return (
    <div className="text-center">
      {/* QRCode bileşeni burada kullanılıyor */}
      <QRCodeSVG value={menuUrl} size={256} />
      <p className="mt-3">
        Bu QR kodu, şubenizin menü sayfasına yönlendirir.<br/>
        URL: <strong>{menuUrl}</strong>
      </p>
    </div>
  );
};

export default QrCodeGenerator;
