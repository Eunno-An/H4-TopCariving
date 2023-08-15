import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

interface completeOptionInterface {
  photoUrl: string;
  optionName: string;
  optionDetail: string;
  price: number;
}

export const CompleteOptionCard = ({
  photoUrl,
  optionName,
  price,
  optionDetail,
}: completeOptionInterface) => {
  return (
    <CompleteFlex width={462} height="auto" padding="20px" gap={20}>
      <Flex width={69} height={69}>
        <ImgContainer src={photoUrl} alt="" />
      </Flex>
      <Flex direction="column" gap={10}>
        <Flex justify="space-between">
          <Text typo="Body1_Medium">{optionName}</Text>
          <Text typo="Body1_Medium">{price.toLocaleString()}원</Text>
        </Flex>
        <Flex justify="flex-start" align="flex-start">
          <Text typo="Body3_Regular">{optionDetail}</Text>
        </Flex>
      </Flex>
    </CompleteFlex>
  );
};

const CompleteFlex = styled(Flex)`
  border: 2px solid ${theme.palette.Sand};
  border-radius: 20px;
`;

const ImgContainer = styled.img`
  width: 69px;
  height: 69px;
  border-radius: 8px;

  border: 1px solid ${theme.palette.DarkGray};

  box-shadow: 3px 3px 5px ${theme.palette.DarkGray};
`;
