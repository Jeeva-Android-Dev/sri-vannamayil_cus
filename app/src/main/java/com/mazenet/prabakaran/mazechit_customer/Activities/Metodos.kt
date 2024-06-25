package com.mazenet.prabakaran.mazechit_customer.Activities

import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfTemplate
import com.itextpdf.text.pdf.PdfWriter

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Created by MZS119 on 3/23/2018.
 */

 class Metodos :BaseActivity(){
    lateinit var writer: PdfWriter
    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    var compname = ""
    var amounttoat = ""

    fun write(fname: String, fcontent: String, total: String): Boolean? {
        try {
            //String fpath = "/sdcard/" + fname + ".pdf";
            val mediaStorageDir = File(Environment.getExternalStorageDirectory(), "Radient")

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to create directory")
                }
            }
            val dateFormat = java.text.SimpleDateFormat("dd-MM-yyyy")
            dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
            val date1 = Date()
            val date = dateFormat.format(date1)

            val filename = "$fname.pdf"
            val file = File(mediaStorageDir, filename)
            if (!file.exists()) {

                if (file.createNewFile()) {
                    file.createNewFile()
                }
            }
            val link = "$mediaStorageDir/$fname.pdf"

            /* String fpath = Environment.getExternalStorageDirectory().getPath()+fname+".pdf";
            File file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            } */

            val bfBold12 = Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD, BaseColor(0, 0, 0))
            val bf12 = Font(Font.FontFamily.TIMES_ROMAN, 12f)
            val titleFontcell = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18f, BaseColor.BLUE)
            val titleFontcel2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18f, BaseColor.BLACK)
            val titleFontcel4 = FontFactory.getFont(FontFactory.TIMES_BOLD, 18f, BaseColor.BLACK)
            val titleFontcel3 = FontFactory.getFont(FontFactory.COURIER_OBLIQUE, 16f, BaseColor.BLACK)
            val titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 24f, BaseColor.RED)

            val document = Document()

            writer = PdfWriter.getInstance(
                document,
                FileOutputStream(file.absoluteFile)
            )
            document.open()

            document.add(Paragraph(Element.ALIGN_MIDDLE.toFloat(), "CHIT FUNDS", titleFont))
            document.add(Paragraph("\n"))
            document.add(Paragraph( date))

            document.add(Paragraph("\n"))
            document.add(Anchor("           Company : " + fcontent, titleFontcell))
            //            Transactionmodel tmod = transdetails.get(len-1);

            document.add(Paragraph("            To Party : " + compname, titleFontcell))
            document.add(Paragraph("\n"))
            document.add(Paragraph("\n"))

            document.add(Paragraph("\n"))
            document.add(Anchor("           Total  : Rs. " + amounttoat + " -/", titleFontcel4))
            // Image image1 = Image.getInstance("http://i.imgur.com/DvpvklR.png");
            //document.add(image1);
            //Image image1 = Image.getInstance("C:/image1.jpg");
            //Image image2 = Image.getInstance("C:/image2.jpg");
            val byte1 = writer.getDirectContent()
            val tp1 = byte1.createTemplate(600f, 150f)
            // tp1.addImage(image2);

            val byte2 = writer.getDirectContent()
            val tp2 = byte2.createTemplate(600f, 150f)
            //   tp2.addImage(image1);

            byte1.addTemplate(tp1, 0f, 715f)
            byte2.addTemplate(tp2, 0f, 0f)
//            val phrase1 = Phrase(byte1+"", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7f, Font.NORMAL))
//            val phrase2 = Phrase(byte2+"", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7f, Font.NORMAL))
            // image1.setAbsolutePosition(0, 0);
            // image2.setAbsolutePosition(0, 0);


            /* HeaderFooter header = new HeaderFooter(phrase1, true);
             HeaderFooter footer = new HeaderFooter(phrase2, true);
             document.setHeader(header);
             document.setFooter(footer); */
            document.close()

            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } catch (e: DocumentException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return false
        }

    }/*
    public static PdfPCell getNormalCell(String string, String language, float size)
            throws DocumentException, IOException {
        if(string != null && "".equals(string)){
            return new PdfPCell();
        }
        Font f  = getFontForThisLanguage(language);
        if(size < 0) {
            f.setColor(BaseColor.RED);
            size = -size;
        }
        f.setSize(size);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    public static Font getFontForThisLanguage(String language) {
        if ("czech".equals(language)) {
            return FontFactory.getFont(FONT, "Cp1250", true);
        }
        if ("greek".equals(language)) {
            return FontFactory.getFont(FONT, "Cp1253", true);
        }
        return FontFactory.getFont(FONT, null, true);
    } */
}

