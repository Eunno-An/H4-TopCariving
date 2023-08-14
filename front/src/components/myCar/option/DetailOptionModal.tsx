import { Flex, ModalContainerCss, Text, backCss } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';
import styled from '@emotion/styled';
import { KeyOfPalette, theme } from '@styles/theme';
import { ColorCircle } from '..';
import close from '@assets/images/close.svg';

export interface alertButtonInterface {
  text: string;
  color: KeyOfPalette;
  onClick: () => void;
}

export const DetailOptionModal = ({
  closeModal,
}: {
  closeModal: () => void;
}) => {
  const { myCarInfo } = useMyCar();

  return (
    <Flex css={ModalContainerCss}>
      <div css={backCss} onClick={closeModal}></div>
      <ModalContainer>
        <CloseIcon src={close} onClick={closeModal} />
        <Flex height={80}>
          <Text typo="Heading4_Medium">견적요약보기</Text>
        </Flex>
        <Title>
          <Text>총 견적 금액</Text>
          <Flex justify="flex-end" gap={2}>
            <Text typo="Heading4_Bold">
              {myCarInfo.price.toLocaleString('ko-KR')}
            </Text>
            <Text typo="Body4_Medium">원</Text>
          </Flex>
        </Title>
        <ContentWrapper>
          <Content>
            <p>{`펠리세이드 ${myCarInfo.trim.type?.name}`}</p>
            <p>{`${myCarInfo.trim.type?.price.toLocaleString('ko-KR')}원`}</p>
          </Content>
          <Content>
            <p>
              {Object.values(myCarInfo.trim)
                .map((trim, idx) =>
                  idx !== 0 && trim !== null ? trim.name : null,
                )
                .filter((name) => name !== null)
                .join('/')}
            </p>
            <p>
              {`+${Object.values(myCarInfo.trim)
                .map((trim, idx) => (idx !== 0 && trim?.price) || 0) // 각 trim의 price 값 또는 0으로 매핑
                .reduce(
                  (accumulator, currentValue) => accumulator + currentValue,
                  0,
                )
                .toLocaleString('ko-KR')}원`}
            </p>
          </Content>
        </ContentWrapper>
        <Title>색상</Title>
        <ContentWrapper>
          <Content gap={12}>
            <Flex gap={12} width="auto">
              <p>외장</p>
              <ColorCircle src={myCarInfo.color.exteriorColor?.url} />
              <p>{myCarInfo.color.exteriorColor?.name}</p>
            </Flex>
            <div>{`+${myCarInfo.color.exteriorColor?.price.toLocaleString(
              'ko-KR',
            )}원`}</div>
          </Content>
          <Content gap={12}>
            <Flex gap={12} width="auto">
              <p>내장</p>
              <ColorCircle src={myCarInfo.color.interiorColor?.url} />
              <p>{myCarInfo.color.interiorColor?.name}</p>
            </Flex>
            <div>{`+${myCarInfo.color.interiorColor?.price.toLocaleString(
              'ko-KR',
            )}원`}</div>
          </Content>
        </ContentWrapper>
        <Title>{`선택 옵션 ${myCarInfo.selectedOption.length}`}</Title>
        <ContentWrapper>
          {myCarInfo.selectedOption.map((item) => (
            <Content>
              <p>{item.name}</p>
              <div>{`+${item.price.toLocaleString('ko-KR')}원`}</div>
            </Content>
          ))}
          <Content></Content>
        </ContentWrapper>
      </ModalContainer>
    </Flex>
  );
};

const ModalContainer = styled(Flex)`
  position: relative;
  flex-direction: column;

  width: 476px;
  height: auto;
  z-index: 20;

  border-radius: 20px;
  background: #fff;

  /* 팝업창그림자 */
  box-shadow: 0px 12px 20px 0px rgba(0, 0, 0, 0.1);
`;

export const CloseIcon = styled.img`
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 25;
  cursor: pointer;
`;

const Title = styled(Flex)`
  justify-content: space-between;

  height: 42px;
  padding: 0 20px;
  background-color: ${theme.palette.LightSand};
  ${theme.typo.Body3_Medium};
  white-space: nowrap;
`;

const ContentWrapper = styled(Flex)`
  flex-direction: column;
  padding: 20px;
  gap: 15px;
`;
const Content = styled(Flex)`
  ${theme.typo.Body3_Regular};
  justify-content: space-between;
`;
