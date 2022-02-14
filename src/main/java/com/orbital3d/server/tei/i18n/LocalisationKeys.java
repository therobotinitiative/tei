package com.orbital3d.server.tei.i18n;

import com.orbital3d.server.tei.i18n.type.LocalisationKey;

/**
 * Localisation keys. Tagged as {@link LocalisationKey} type to tighten the type
 * safety when using the localisation keys. Also reduces errors caused by topys.
 * 
 * @author msiren
 *
 */
public enum LocalisationKeys implements LocalisationKey
{
	DASHBOARD,
	VIEW,
	TEMPLATE,
	SEND,
	LOGOUT,
	WELCOME,
	SAVE,
	CANCEL,
	NEW,
	NEW_TEMPLATE,
	ADD,
	REMOVE,
	OPTION,
	OPEN,
	CLOSE,
	QUESTION,
	USER_NAME,
	PASSWORD,
	LOGIN,
	FIRST_NAME,
	LAST_NAME,
	UPDATE,
	ERROR,
	INFORMATION,
	USERS,
	CHANGE_PASSWORD,
	USER_DATA,
	EMAIL,
	PERMISSIONS,
	UPDATE_PERMISSIONS,
	ERROR_401_MESSAGE,
	ERROR_401_DESCRIPTION,
	ERROR_408_MESSAGE,
	ERROR_408_DESCRIPTION,
	ERROR_413_MESSAGE,
	ERROR_413_DESCRIPTION,
	ERROR_404_MESSAGE,
	ERROR_404_DESCRIPTION,
	ERROR_UNKNOWN_MESSAGE,
	ERROR_UNKNOW_DESCRIPTION,
	INFORMATION_USER_ADDED,
	INFORMATION_PASSWORD_CHANGED,
	INFORMATION_PERMISSIONS_UPDATED,
	INFORMATION_USER_DELETED,
	INFORMATION_USER_DATA_UPDATED,
	ADMINISTRATOR;
}
