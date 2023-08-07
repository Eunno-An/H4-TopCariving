import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { myCarFooterInterface } from '@interface/index';
import { myCarUrl } from '@pages/MyCar';
import { Dispatch, SetStateAction } from 'react';
import { useNavigate } from 'react-router-dom';

interface footerProps {
  currentUrl: string;
  setCurrentUrl: Dispatch<SetStateAction<string>>;
  footerInfo: myCarFooterInterface;
}

export const Footer = ({
  currentUrl,
  setCurrentUrl,
  footerInfo,
}: footerProps) => {
  const navigate = useNavigate();

  const onClickButton = (moveNum: number) => {
    const nextIdx = myCarUrl.indexOf(currentUrl) + moveNum;
    const nextUrl = myCarUrl[nextIdx];

    navigate(nextUrl);
    setCurrentUrl(nextUrl);
  };

  return (
    <Flex
      backgroundColor="Sand"
      height={108}
      borderRadius="16px 16px"
      padding="12px 36px 24px 36px"
      justify="space-between"
    >
      <Flex width="auto">
        <Section width={208}>
          <Text typo="Body3_Regular" palette="DarkGray">
            트림
          </Text>
          <Text typo="Heading4_Bold" palette="Black">
            {footerInfo.name[0]}
          </Text>
          <Text typo="Body3_Medium" palette="Black">
            {footerInfo.name[1]}
          </Text>
        </Section>
        <img src="/image/page/myCar/columnLine.svg" />
        <Section width={208}>
          <Text typo="Body3_Regular" palette="DarkGray">
            선택 색상
          </Text>
          <Flex justify="start" gap={12}>
            <Text typo="Body3_Regular" palette="Black">
              외장
            </Text>
            <Text></Text>
          </Flex>
          <Flex justify="start" gap={12}>
            <Text typo="Body3_Regular" palette="Black">
              내장
            </Text>
            <Text></Text>
          </Flex>
        </Section>
        <img src="/image/page/myCar/columnLine.svg" />
        <Section width={380}>
          <Text typo="Body3_Regular" palette="DarkGray">
            선택 옵션
          </Text>
        </Section>
        <img src="/image/page/myCar/columnLine.svg" />
        <Section width={380}>
          <Text typo="Body3_Regular" palette="DarkGray">
            예상 가격
          </Text>
          <Flex width="auto">
            <Text typo="Heading1_Bold" palette="Black">
              {`${footerInfo.price.toLocaleString('ko-KR')}`}
            </Text>
            <Text typo="Body3_Regular" palette="Black">
              원
            </Text>
          </Flex>
        </Section>
      </Flex>

      <Flex gap={7} width="auto">
        {myCarUrl.indexOf(currentUrl) != myCarUrl.length - 1 && (
          <>
            {myCarUrl.indexOf(currentUrl) !== 0 && (
              <div onClick={() => onClickButton(-1)}>
                <Button
                  width={121}
                  type="medium"
                  text="이전"
                  backgroundColor="White"
                />
              </div>
            )}

            <div onClick={() => onClickButton(+1)}>
              <Button
                width={176}
                type="medium"
                text="다음 단계로"
                backgroundColor="Primary"
              />
            </div>
          </>
        )}
      </Flex>
    </Flex>
  );
};

const Section = styled(Flex)`
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  gap: 6px;
  padding: 0 0 0 22px;
  box-sizing: border-box;
`;
