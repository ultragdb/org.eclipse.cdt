/*
 *  Copyright © 2011 Christian Persch
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef TERMINAL_DEFINES_H
#define TERMINAL_DEFINES_H

G_BEGIN_DECLS
;
#define TERMINAL_APPLICATION_ID                 "org.gnome.Terminal"

#define TERMINAL_OBJECT_PATH_PREFIX             "/org/gnome/Terminal"
#define TERMINAL_OBJECT_INTERFACE_PREFIX        "org.gnome.Terminal"

#define TERMINAL_FACTORY_OBJECT_PATH            TERMINAL_OBJECT_PATH_PREFIX "/Factory0"
#define TERMINAL_FACTORY_INTERFACE_NAME         TERMINAL_OBJECT_INTERFACE_PREFIX ".Factory0"

#define TERMINAL_RECEIVER_OBJECT_PATH_FORMAT    TERMINAL_OBJECT_PATH_PREFIX "/window/%u/terminal/%s"
#define TEMRINAL_RECEIVER_INTERFACE_NAME        TERMINAL_OBJECT_INTERFACE_PREFIX ".Terminal0"

#define TERMINAL_SEARCH_PROVIDER_PATH           TERMINAL_OBJECT_PATH_PREFIX "/SearchProvider"

G_END_DECLS

#endif /* !TERMINAL_DEFINES_H */
