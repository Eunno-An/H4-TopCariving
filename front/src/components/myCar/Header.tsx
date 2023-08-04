import { Flex } from '../common/Flex';
import { Text } from '../common/Text';
import hyundai from '../../assets/images/hyundai.svg';
import cargo from '../../assets/images/cargo.svg';
import save from '../../assets/images/save.svg';
import vector845 from '../../assets/images/vector845.svg';
import vector850 from '../../assets/images/vector850.svg';

const Header = () => {
  return (
    <Flex
      backgroundColor="Sand"
      height={60}
      justify="space-between"
      padding="0 120px 0 84px"
    >
      <Flex gap={9.58} justify="flex-start">
        <img src={hyundai} alt="현대자동차 로고" />
        <img src={vector845} alt="벽" />
        <Text typo="Body4_Medium">내 차 만들기</Text>
      </Flex>

      <Flex justify="flex-end" gap={21}>
        <Flex width="auto" gap={3}>
          <Text typo="Body4_Medium">자동 저장중</Text>
          <img src={save} alt="" />
        </Flex>
        <Flex gap={16} width="auto">
          <Text typo="Body2_Medium" palette="DarkGray">
            펠리세이드
          </Text>
          <img src={vector850} alt="" />
          <Flex
            backgroundColor="Black"
            borderRadius="18px"
            width={98}
            height={35}
            gap={4}
          >
            <img src={cargo} alt="아카이빙 카고" />
            <Text palette="Sand" typo="Body4_Medium">
              아카이빙
            </Text>
          </Flex>
        </Flex>
      </Flex>
    </Flex>
  );
};

export default Header;
