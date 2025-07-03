/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;

import java.util.Map;

public enum ZkEvent {
    onSinglePersonSelected, onSinglePersonCreated, onSinglePersonUpdated,

    onNotificationSelected, onNotificationCreated, onNotificationUpdated,

    onLegalPersonSelected, onLegalPersonCreated, onLegalPersonUpdated,

    onClientSelected, onClientCreated, onClientUpdated,

    onInvoiceItemCreated, onInvoiceItemUpdated, onInvoiceItemSelected,

    onInvoiceCreated, onInvoiceUpdated, onInvoiceSelected, onInvoiceListSelected,

    onArticleCreated, onArticleSelected, onArticleUpdated,

    onMeasurementCreated, onMeasurementUpdated, onMeasurementSelected,

    onHouseholdCreated, onHouseholdUpdated, onHouseholdSelected,

    onDiscountCreated, onDiscountUpdated, onDiscountSelected,

    onFineForgivenessCreated, onFineForgivenessUpdated, onFineForgivenessSelected,

    onBoatCreated, onBoatUpdated, onBoatSelected,

    onBoatTypeCreated, onBoatTypeUpdated, onBoatTypeSelected,

    onBoatClassCreated, onBoatClassUpdated, onBoatClassSelected,

    onBoatSpaceCreated, onBoatSpaceUpdated, onBoatSpaceSelected,

    onBoatSpaceTypeCreated, onBoatSpaceTypeUpdated, onBoatSpaceTypeSelected,

    onBoatSpaceClassCreated, onBoatSpaceClassUpdated, onBoatSpaceClassSelected,

    onBoatOwnerCreated, onBoatOwnerUpdated, onBoatOwnerSelected,

    onBoatDepartureToSeaCreated, onBoatDepartureToSeaUpdated, onBoatDepartureToSeaSelected,

    onAllergyCreated, onAllergySelected, onAllergyUpdated,

    onModalityCreated, onModalitySelected, onModalityUpdated,

    onCountryCreated, onCountrySelected, onCountryUpdated,

    onProfessionCreated, onProfessionSelected, onProfessionUpdated,

    onPermissionCreated, onPermissionSelected, onPermissionUpdated,

    onUserCreated, onUserSelected, onUserUpdated,

    onUserGroupCreated, onUserGroupSelected, onUserGroupUpdated,

    onMemberCreated, onMemberSelected, onMemberUpdated,

    onPaymentOrderCreated, onPaymentOrderSelected, onPaymentOrderUpdated,

    onMemberInvoiceCreated, onMemberInvoiceSelected, onMemberInvoiceUpdated,

    onMemberReceiptCreated, onMemberReceiptSelected, onMemberReceiptUpdated,

    onMemberTypeCreated, onMemberTypeSelected, onMemberTypeUpdated,

    onFeeCreated, onFeeSelected, onFeeUpdated,

    onFeeValueCreated, onFeeValueSelected, onFeeValueUpdated,

    onFeeFineIntervalCreated, onFeeFineIntervalSelected, onFeeFineIntervalUpdated,

    onIdentificationDocumentTypeCreated, onIdentificationDocumentTypeUpdated, onIdentityDocumentTypeSelected,

    onIdentificationDocumentTypeDenominationCreated, onIdentificationDocumentTypeDenominationUpdated, onIdentityDocumentTypeDenominationSelected,

    onServiceInvoiceCreated, onServiceInvoiceUpdated, onServiceInvoiceSelected,
    onServiceCreated, onServiceUpdated, onServiceSelected,
    onServiceInvoiceItemCreated, onServiceInvoiceItemUpdated, onServiceInvoiceItemSelected,

    onImmediateReceiptView,

    onBillingNoticeSelected, onBillingNoticeCreated, onBillingNoticeUpdated,

    onMemberReviewGiven,

    onReportTypeSelected,

    onReportClassificationSelected,

    onInstituitionSelected,
    onTeamSelected,
    onReportSelected,

    //
    ;

    public <T extends org.zkoss.zk.ui.event.Event> void listen(Component component, EventListener<T> eventListener) {
        component.addEventListener(this.name(), eventListener);
    }

    public void post(Component component, Map<String, Object> arguments) {
        org.zkoss.zk.ui.event.Events.postEvent(this.name(), component, arguments);
        BindUtils.postGlobalCommand(null, null, this.name(), arguments);
    }

    public void post(Component component) {
        org.zkoss.zk.ui.event.Events.postEvent(this.name(), component, null);
        BindUtils.postGlobalCommand(null, null, this.name(), null);
    }

    public void post(Component component, org.zkoss.zk.ui.event.Event event) {
        org.zkoss.zk.ui.event.Events.postEvent(this.name(), component, event.getData());
        BindUtils.postGlobalCommand(null, null, this.name(), (Map<String, Object>) event.getData());
    }

}
