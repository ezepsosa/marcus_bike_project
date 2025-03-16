import styled from "styled-components";
import { GeneralColors } from "../../styles/sharedStyles";

export const Container = styled.div`
  height: 40rem;
  width: 37rem;
  margin: 1rem;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #fff;
  border-radius: 1.5rem;
  background-color: ${GeneralColors.backgroundSecondary};
`;

export const GridProducts = styled.div`
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(20rem, 1fr));
  gap: 2rem;
  padding: 1rem;
`;

export const Section = styled.section`
  width: 100%;
  display: flex;
  padding: 1rem;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: ${GeneralColors.backgroundPrimary};
  flex-grow: 1;
`;

export const ProductCard = styled.div`
  width: 15rem;
  height: 20rem;
  padding:1rem;
  display: flex;
  flex-direction:column;
  align-items: center;
  text-align: center;
  border: 1px solid #fff;
  border-radius: 2rem;
    transition: background 0.3s, transform 0.2s;
  margin: 0.5rem;
  &:hover {
    background-color: ${GeneralColors.SecondayButtonHover};
    transform: scale(1.05);
  }
}
`;

export const ProductImage = styled.img`
  height: 12rem;
  width: 12rem;
  object-fit: contain;
`;

export const ProductInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  place-items: flex-start;
`;

export const Form = styled.form``;
