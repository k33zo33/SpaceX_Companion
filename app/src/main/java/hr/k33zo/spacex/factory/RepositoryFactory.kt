package hr.k33zo.spacex.factory

import android.content.Context
import hr.k33zo.spacex.dao.SpaceXSqlHelper

fun getRepository(context: Context?)=SpaceXSqlHelper(context)