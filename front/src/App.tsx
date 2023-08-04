import { BrowserRouter, Route, Routes } from 'react-router-dom';
import MyCar from './pages/MyCar';
import styled from '@emotion/styled';

function App() {
  return (
    <Container>
      <BrowserRouter>
        <Routes>
          <Route path="/my-car/trim" element={<MyCar />} />
          <Route path="/my-car/trim/engine" element={<MyCar />} />
          <Route path="/my-car/trim/bodytype" element={<MyCar />} />
          <Route path="/my-car/trim/traction" element={<MyCar />} />
          <Route path="/my-car/color" element={<MyCar />} />
          <Route path="/my-car/option" element={<MyCar />} />
          <Route path="/my-car/option/genuine" element={<MyCar />} />
          <Route path="/my-car/options/performance" element={<MyCar />} />
          <Route path="/my-car/complete" element={<MyCar />} />
        </Routes>
      </BrowserRouter>
    </Container>
  );
}

const Container = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default App;
