import { Flex } from '../../components/common/Flex';
import Footer from '../../components/myCar/Footer';
import NavBar from '../../components/myCar/NavBar';
import Header from '../../components/myCar/Header';
import Trim from './Trim';

const MyCar = () => {
  return (
    <Flex direction="column" align="center">
      <Header />
      <NavBar />
      <Flex direction="column" width={1040}>
        <Trim />
      </Flex>
      <Footer />
    </Flex>
  );
};

export default MyCar;
