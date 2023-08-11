import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import vector481 from '@assets/images/Vector 481.svg';
import { theme } from '@styles/theme';

interface completeOptionInterface {
  photoUrl: string;
  optionName: string;
  price: number;
  details: string[];
}

export const CompleteOptionCard = ({
  photoUrl,
  optionName,
  price,
  details,
}: completeOptionInterface) => {
  return (
    <CompleteFlex width={462} height={99} gap={20}>
      <Flex width={69} height={69}>
        <ImgContainer src={photoUrl} alt="" />
      </Flex>
      <Flex direction="column">
        <Flex justify="flex-start" gap={8}>
          <Text typo="Body3_Medium">{optionName}</Text>
          <img src={vector481} alt="" />
          <Text typo="Body3_Medium">{price.toLocaleString('ko-KR')}원</Text>
        </Flex>
        <Flex align="flex-start">
          <Text typo="Body3_Regular">
            {details.map((it, idx) =>
              idx + 1 !== details.length ? <>{it} / </> : <>{it}</>,
            )}
          </Text>
        </Flex>
      </Flex>
    </CompleteFlex>
  );
};

const CompleteFlex = styled(Flex)`
  border-top: 1px solid ${theme.palette.Sand};
  border-bottom: 1px solid ${theme.palette.Sand};
`;

const ImgContainer = styled.img`
  width: 69px;
  height: 69px;
  border-radius: 8px;

  border: 1px solid ${theme.palette.DarkGray};

  box-shadow: 3px 3px 5px ${theme.palette.DarkGray};
`;
