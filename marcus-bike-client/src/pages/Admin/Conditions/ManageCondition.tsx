import { useEffect, useState } from "react";
import {
  PrimaryButton,
  PrimaryTitle,
  TableButton,
  Table,
  Tbody,
  TdBody,
  ThBody,
  Thead,
  TrTable,
  ButtonContainer,
} from "../../../components/styles";
import { Container, Section } from "./styles";
import {
  deleteCondition,
  getPartsConditions,
  getProductParts,
} from "../../../server/api";
import { ProductPart } from "../../../models/productPart";
import {
  ProductPartCondition,
  ProductPartConditionExtended,
} from "../../../models/productPartCondition";
import { ModalManageConditions } from "./ModalManageCondition/ModalManageConditions";

/**
 * Component for managing product part conditions.
 * Allows users to view, add, and delete conditions between parts.
 */

export const ManageConditions = () => {
  const [productParts, setProductParts] = useState<ProductPart[]>([]);
  const [informationalConditions, setInformationalConditions] =
    useState<ProductPartConditionExtended[]>();
  const [conditions, setConditions] = useState<ProductPartCondition[]>([]);
  const [isProductModalOpen, setIsProductModalOpen] = useState<boolean>(false);

  /**
   * Effect to transform raw conditions into a structured format with product part details.
   * Runs when conditions or product parts change.
   */
  useEffect(() => {
    function createInformationalConditions() {
      const res: ProductPartConditionExtended[] = [];
      for (const condition of conditions) {
        const productPart = productParts.find(
          (part: ProductPart) => part.id === condition.partId
        );
        const dependantProductPart = productParts.find(
          (part: ProductPart) => part.id === condition.dependantPartId
        );
        if (productPart && dependantProductPart) {
          const productPartExtended: ProductPartConditionExtended = {
            part: productPart,
            dependantPart: dependantProductPart,
            isRestriction: condition.isRestriction,
            priceAdjustment: condition.priceAdjustment,
          };
          res.push(productPartExtended);
        }
      }
      setInformationalConditions(res);
    }
    createInformationalConditions();
  }, [conditions, productParts]);

  /**
   * Effect to load product parts and conditions from the API on component mount.
   */

  useEffect(() => {
    async function loadConditions() {
      setConditions(await getPartsConditions());
    }
    async function loadProductParts() {
      setProductParts(await getProductParts());
    }

    loadConditions();
    loadProductParts();
  }, []);

  /**
   * Handles the deletion of a product part condition.
   * Calls the API and updates the state by removing the deleted condition.
   */
  async function handleDeleteCondition(
    partId: number,
    dependantPartId: number
  ) {
    try {
      await deleteCondition(partId, dependantPartId);
      setConditions(
        conditions.filter(
          (p: ProductPartCondition) =>
            p.partId != partId && p.dependantPartId != dependantPartId
        )
      );
    } catch (error) {
      console.error(
        "Error deleting product part with id and dependant part Id",
        partId,
        dependantPartId
      );
    }
  }

  return (
    <Section>
      <Container>
        <PrimaryTitle $fontSize={"2.4rem"}>Manage your conditions</PrimaryTitle>
        <PrimaryButton
          $backgroundColor="#f83"
          onClick={() => {
            setIsProductModalOpen(true);
          }}
        >
          Add condition
        </PrimaryButton>
        <Table>
          <Thead>
            <TrTable>
              <ThBody>Primary part</ThBody>
              <ThBody>Primary part type</ThBody>
              <ThBody>Secondary part</ThBody>
              <ThBody>Secondary part type</ThBody>
              <ThBody>Extra price</ThBody>
              <ThBody>Restriction</ThBody>
              <ThBody>Action</ThBody>
            </TrTable>
          </Thead>
          <Tbody>
            {informationalConditions &&
              informationalConditions.map(
                (
                  informationalCondition: ProductPartConditionExtended,
                  index
                ) => {
                  return (
                    <TrTable key={index}>
                      <TdBody>{informationalCondition.part.partOption}</TdBody>
                      <TdBody>
                        {
                          informationalCondition.part.productPartCategory
                            .toString()
                            .split("_")[0]
                        }
                      </TdBody>
                      <TdBody>
                        {informationalCondition.dependantPart.partOption}
                      </TdBody>
                      <TdBody>
                        {
                          informationalCondition.dependantPart.productPartCategory
                            .toString()
                            .split("_")[0]
                        }
                      </TdBody>
                      <TdBody>
                        {String(informationalCondition.priceAdjustment)}
                      </TdBody>
                      <TdBody>
                        {String(informationalCondition.isRestriction)}
                      </TdBody>
                      <TdBody>
                        <ButtonContainer>
                          <TableButton
                            type="button"
                            $backgroundColor="red"
                            onClick={() =>
                              handleDeleteCondition(
                                informationalCondition.part.id,
                                informationalCondition.dependantPart.id
                              )
                            }
                          >
                            Delete
                          </TableButton>
                        </ButtonContainer>
                      </TdBody>
                    </TrTable>
                  );
                }
              )}
          </Tbody>
        </Table>
        <ModalManageConditions
          isOpen={isProductModalOpen}
          setIsOpen={setIsProductModalOpen}
          productParts={productParts}
          changeConditions={setConditions}
          conditions={conditions}
        />
      </Container>
    </Section>
  );
};
