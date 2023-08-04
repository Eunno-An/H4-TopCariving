import { Flex } from '../common/Flex';
import arrowRight from '../../assets/images/arrowRight.svg';
import { Text } from '../common/Text';
import vector455 from '../../assets/images/vector455.svg';

const NavBar = () => {
  return (
    <Flex backgroundColor="LightSand" height={60} padding="0 120px 0 120px">
      <Flex gap={12} justify="flex-start">
        <Flex gap={5} width="auto">
          <Flex
            borderRadius="50%"
            backgroundColor="Black"
            width={20}
            height={20}
          >
            <Text palette="Neutral" typo="Body4_Medium">
              1
            </Text>
          </Flex>
          <li>
            <Text palette="Black" typo="Body2_Medium">
              트림선택
            </Text>
          </li>
        </Flex>
        <Flex width="auto">
          <li>
            <Text palette="MediumGray" typo="Body3_Medium">
              엔진
            </Text>
          </li>
          <li>
            <img src={arrowRight} alt="" />
          </li>
          <li>
            <Text palette="MediumGray" typo="Body3_Medium">
              바디타입
            </Text>
          </li>
          <li>
            <img src={arrowRight} alt="" />
          </li>
          <li>
            <Text palette="MediumGray" typo="Body3_Medium">
              구동방식
            </Text>
          </li>
        </Flex>
      </Flex>

      <Flex justify="flex-end">
        <Flex
          borderRadius="50%"
          backgroundColor="LightGray"
          width={20}
          height={20}
        >
          <Text palette="Neutral" typo="Body4_Medium">
            2
          </Text>
        </Flex>
        <img src={vector455} alt="" />
        <Flex
          borderRadius="50%"
          backgroundColor="LightGray"
          width={20}
          height={20}
        >
          <Text palette="Neutral" typo="Body4_Medium">
            3
          </Text>
        </Flex>
      </Flex>
    </Flex>
  );
};

export default NavBar;
