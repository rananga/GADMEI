//------------------------------------------------------------------------------
// <auto-included>
//     This code was included by a nuget package.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the nuget is reinstalled.
//     
//     If any modifications are required, please contact the nuget package
//     developer
// </auto-included>
//------------------------------------------------------------------------------

using System.Diagnostics.CodeAnalysis;
using System;
using System.Collections.Generic;
using System.Reflection;

namespace Sana.Extensions
{
    /// <summary>
    /// This class is responsible to generate generic messages.
    /// </summary>
    [ExcludeFromCodeCoverage]
    public class MessageHelper
    {
        protected string extensionName;

        protected string ExtensionVersion => Assembly.GetExecutingAssembly().GetName().Version.ToString();

        /// <summary>
        /// Initializes a new instance of the <see cref="MessageHelper"/> class.
        /// </summary>
        /// <param name="extensionName"></param>
        public MessageHelper(string extensionName)
        {
            this.extensionName = extensionName;
        }

        /// <summary>
        /// Gets the value not equal message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <param name="expectedValue">The expected value.</param>
        /// <returns>Returns the value not equal message.</returns>
        public virtual string ValueNotEqualMessage(string fieldName, string expectedValue)
        {
            return string.Format("Extension '{0} [{1}]': Expected value '{2}' is not in the field '{3}'.", extensionName, ExtensionVersion, expectedValue, fieldName);
        }

        /// <summary>
        /// Gets the value not equal message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <returns>Returns the value not equal message.</returns>
        public virtual string ValueNotEqualMessage(string fieldName)
        {
            return string.Format("Extension '{0} [{1}]': Expected value is not in the field '{2}'.", extensionName, ExtensionVersion, fieldName);
        }

        /// <summary>
        /// Gets the value is empty message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <returns>Returns the value is empty message.</returns>
        public virtual string ValueIsEmptyMessage(string fieldName)
        {
            return string.Format("Extension '{0} [{1}]': Value is mandatory for the field '{2}'.", extensionName, ExtensionVersion, fieldName);
        }

        /// <summary>
        /// Gets the signature validation failed message.
        /// </summary>
        /// <returns>Returns the signature validation failed message.</returns>
        public virtual string SignatureValidationFailedMessage()
        {
            return string.Format("Extension '{0} [{1}]': Signature validation failed.", extensionName, ExtensionVersion);
        }

        /// <summary>
        /// Gets the length validation message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <param name="maxLength">The maximum length.</param>
        /// <returns>Returns the length validation message.</returns>
        public virtual string LengthExceedMessage(string fieldName, int maxLength)
        {
            return string.Format("Extension '{0} [{1}]': Value of '{2}' cannot be longer than {3} characters.", extensionName, ExtensionVersion, fieldName, maxLength);
        }

        /// <summary>
        /// Gets the special characters validation message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <param name="fieldValue">Validating field value.</param>
        /// <param name="exemptedCharacterList">List of exempted special character(s) [@,#,^]</param>
        /// <returns>Returns the special characters validation message.</returns>
        public virtual string SpecialCharactersMessage(string fieldName, string fieldValue, IList<string> exemptedCharacterList)
        {
            var exemptedCharacters = (exemptedCharacterList != null && exemptedCharacterList.Count > 0) ? string.Join(",", exemptedCharacterList) : string.Empty;
            var exemptedlist = (!string.IsNullOrWhiteSpace(exemptedCharacters)) ? string.Format(" except '{0}'", exemptedCharacters) : string.Empty;
            return string.Format("Extension '{0} [{1}]': Special characters are not allowed for '{2}'{3}. Provided value is '{4}'.", extensionName, ExtensionVersion, fieldName, exemptedlist, fieldValue);
        }

        /// <summary>
        /// Gets the range validation message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <param name="fieldValue">Validating field value.</param>
        /// <param name="minRange">Minimum value of the range.</param>
        /// <param name="maxRange">Maximum value of the range.</param>
        /// <returns>Returns the special characters validation message.</returns>
        public virtual string RangeValidationMessage<T>(string fieldName, T fieldValue, T minRange, T maxRange) where T : struct, IComparable, IFormattable, IConvertible
        {
            return string.Format("Extension '{0} [{1}]': Value of '{2}' must be in the range of {3} and {4}. Provided value is '{5}'.", extensionName, ExtensionVersion, fieldName, minRange, maxRange, fieldValue);
        }

        /// <summary>
        /// Gets the range validation message.
        /// </summary>
        /// <param name="fieldName">The name of the field.</param>
        /// <param name="fieldValue">Validating field value.</param>
        /// <param name="limit">Decimal value limit.</param>
        /// <returns>Returns the special characters validation message.</returns>
        public virtual string DecimalPlacesValidationMessage<T>(string fieldName, T fieldValue, int limit) where T : struct, IComparable, IFormattable, IConvertible
        {
            return string.Format("Extension '{0} [{1}]': Maximum decimal places for '{2}' is '{3}'. Provided value is '{4}'.", extensionName, ExtensionVersion, fieldName, limit, fieldValue);
        }
    }
}
