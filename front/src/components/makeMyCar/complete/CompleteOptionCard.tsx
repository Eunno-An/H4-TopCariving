import { Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

interface completeOptionInterface {
  photoUrl: string;
  optionName: string;
  optionDetail: string;
  price: number;
  childOptions: string[];
}

export const CompleteOptionCard = ({
  photoUrl,
  optionName,
  price,
  childOptions,
}: completeOptionInterface) => {
  return (
    <CompleteFlex width={462} height="auto" padding="20px" gap={30}>
      <Flex width={69} height={69}>
        <ImgContainer src={photoUrl} alt="" />
      </Flex>
      <Flex direction="column" height={60} gap={10}>
        <Flex justify="space-between" align="flex-start">
          <Text typo="Body1_Medium">{optionName}</Text>
          <Text
            typo="Body1_Medium"
            css={css`
              display: flex;
              justify-content: flex-end;
              width: 180px;
            `}
          >
            {price.toLocaleString()}원
          </Text>
        </Flex>
        <Flex justify="flex-start" align="flex-start">
          <Text typo="Body3_Regular">{childOptions.join(' / ')}</Text>
        </Flex>
      </Flex>
    </CompleteFlex>
  );
};

const CompleteFlex = styled(Flex)`
  border: 2px solid ${theme.palette.Sand};
  border-radius: 10px;

  &:hover {
    transform: scale(1.01);
    border: 2px solid ${theme.palette.Primary};
  }
  transition: ease 0.5s;
`;

const ImgContainer = styled.img`
  width: 95px;
  height: 95px;

  border-radius: 8px;

  object-fit: cover;
`;
